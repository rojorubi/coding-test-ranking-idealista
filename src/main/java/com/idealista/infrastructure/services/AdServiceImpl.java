package com.idealista.infrastructure.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.configuration.CodingTestRankingConfig;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.InMemoryPersistence;
import com.idealista.infrastructure.persistence.PictureVO;
import com.idealista.infrastructure.utils.constants.EnumOutstandingWord;
import com.idealista.infrastructure.utils.constants.EnumTipology;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdServiceImpl implements AdService{

	@Autowired
	private InMemoryPersistence inMemoryPersistence;
	
	@Autowired
	private CodingTestRankingConfig codingTestRankingConfig;
	
	@Autowired
	private PictureService pictureService;
	
	@Override
	public List<AdVO> getAllScore() {
		
		//cargamos los datos de todos los anuncios y fotos disponibles
		List<AdVO> ads = inMemoryPersistence.getAds();
		List<PictureVO> pictures = inMemoryPersistence.getPictures();
		
		ads.forEach(ad -> {
			if(ad.getScore()==null) {ad.setScore(0);}
			log.info("-----------------------------------");
			log.info("Initial score = "+ad.getScore());
			pictureService.getScoreByPhoto(ad, pictures);
			this.getScoreByDescriptionAndType(ad, ad.getTypology());
			this.getScoreByOutstandingWord(ad);
			this.getScoreCompleteAdAndType(ad, ad.getTypology());
			log.info("Final score = "+ad.getScore());
			log.info("-----------------------------------");
		});
		return ads;
	}
	
	private AdVO getScoreByDescriptionAndType(AdVO ad, String type) {
		Integer countWords=0;
		
		if(ad.getDescription().isEmpty() && !ad.getTypology().equalsIgnoreCase(EnumTipology.GARAJE.getValue())) {
			log.error("Error, the description for ad id ("+ad.getId()+") is empty");
		}else {
			log.info("\t \t Set score ad with desc score=" + codingTestRankingConfig.getScoreAdWithDesc());
			ad.setScore(ad.getScore()+codingTestRankingConfig.getScoreAdWithDesc());
			
			countWords = this.getTotalWordsByDescription(ad.getDescription());
			
			if(ad.getTypology().equalsIgnoreCase(EnumTipology.CHALET.getValue())) {
				if(countWords >= 50) {
					log.info("\t \t Set score by typology "+EnumTipology.CHALET.getValue()+" and countWords="+ countWords + " score=" + codingTestRankingConfig.getScoreWithDescLongType3());
					ad.setScore(ad.getScore()+codingTestRankingConfig.getScoreWithDescLongType3());
				}
			}
			else
			if(ad.getTypology().equalsIgnoreCase(EnumTipology.PISO.getValue())) {
				
				if(countWords > 20 && countWords < 49) {
					log.info("\t \t Set score by typology "+EnumTipology.PISO.getValue()+" and countWords="+ countWords + " score=" + codingTestRankingConfig.getScoreWithDescLongType1());
					ad.setScore(ad.getScore()+codingTestRankingConfig.getScoreWithDescLongType1());
				}else if(countWords >= 50) {
					log.info("\t \t Set score by typology "+EnumTipology.PISO.getValue()+" and countWords="+ countWords + " score=" + codingTestRankingConfig.getScoreWithDescLongType2());
					ad.setScore(ad.getScore()+codingTestRankingConfig.getScoreWithDescLongType2());
				}
			}
		}
		return ad;
	}
	
	private Integer getTotalWordsByDescription(String desc) {
		StringTokenizer stringTokenizer = new StringTokenizer(desc, " ");
	    log.info("\t \t Total words = "+stringTokenizer.countTokens());
		return stringTokenizer.countTokens(); 
	}

	private AdVO getScoreByOutstandingWord(AdVO ad) {
		if(!ad.getDescription().isEmpty()) {
			
			String[] words = Arrays.stream(StringUtils.normalizeSpace(ad.getDescription()).toUpperCase().split(" "))
					  .map(String::trim)
					  .toArray(String[]::new);
			
			List<String> intersect = Arrays.asList(words).stream().filter(EnumOutstandingWord.getListEnumOutstandingWord()::contains).collect(Collectors.toList());
			
			if(!intersect.isEmpty()) {
				intersect.forEach(word -> {
					log.info("\t \t Set score by outstanding word ("+word+") score=" + codingTestRankingConfig.getScoreAdWithOutstandingWords());
					ad.setScore(ad.getScore()+codingTestRankingConfig.getScoreAdWithOutstandingWords());
				});	
			}
		}	
		return ad;
	}
	
	
	private AdVO getScoreCompleteAdAndType(AdVO ad, String type) {
		
		if(ad.getGardenSize()==null) {ad.setGardenSize(0);}
		if(ad.getHouseSize()==null) {ad.setHouseSize(0);}
		
		if(!ad.getDescription().isEmpty()
				&& ad.getPictures().size()>=1) {
			
			if(ad.getTypology().equalsIgnoreCase(EnumTipology.CHALET.getValue())
					&& ad.getGardenSize()!=0
					&& ad.getHouseSize() !=0) {
				log.info("\t \t Set score AD COMPLETE type ("+EnumTipology.CHALET.getValue()+") score=" + codingTestRankingConfig.getScoreAdComplete());
				ad.setScore(ad.getScore() + codingTestRankingConfig.getScoreAdComplete());
			}else if(ad.getTypology().equalsIgnoreCase(EnumTipology.PISO.getValue())
					&& ad.getHouseSize() !=0) {
				log.info("\t \t Set score AD COMPLETE type ("+EnumTipology.PISO.getValue()+") score=" + codingTestRankingConfig.getScoreAdComplete());
				ad.setScore(ad.getScore() + codingTestRankingConfig.getScoreAdComplete());
			}
		}else if(ad.getDescription().isEmpty()
				&& ad.getPictures().size()>=1
				&& ad.getTypology().equalsIgnoreCase(EnumTipology.GARAJE.getValue())
				&& ad.getHouseSize() !=0){
			log.info("\t \t Set score AD COMPLETE type ("+EnumTipology.GARAJE.getValue()+") score=" + codingTestRankingConfig.getScoreAdComplete());
			ad.setScore(ad.getScore() + codingTestRankingConfig.getScoreAdComplete());	
		}
		return ad;
	}

	@Override
	public List<PublicAd> getAllPublicAds() {
		List<AdVO> listAllAds = this.getAllScore();
		List<PublicAd> result = new ArrayList<>();
		
		listAllAds.forEach(ad -> {
			
			if(ad.getScore() > codingTestRankingConfig.getScoreAdPublic()) {
			
				PublicAd publicAd = new PublicAd();
				List<String> urls = new ArrayList<>();
				List<PictureVO> pictures = inMemoryPersistence.getPictures();
				
				publicAd.setDescription(ad.getDescription());
				publicAd.setGardenSize(ad.getGardenSize());
				publicAd.setHouseSize(ad.getHouseSize());
				publicAd.setId(ad.getId());
				publicAd.setTypology(ad.getTypology());
				
				pictureService.getUrlsPictures(ad, urls, pictures);
				
				publicAd.setPictureUrls(urls);
				result.add(publicAd);
			
			}
		});
		
		return result;
	}

	

	@Override
	public List<QualityAd> getAllAdsQualityListing() {
		List<AdVO> listAllAds = this.checkingIrrelevantAds();
		List<AdVO> listAllAdsFiltered = listAllAds.stream().filter(ad -> ad.getScore() < codingTestRankingConfig.getScoreAdPublic()).collect(Collectors.toList());
		List<String> urls = new ArrayList<>();
		List<QualityAd> result = new ArrayList<>();
		List<PictureVO> pictures = inMemoryPersistence.getPictures();
		
		listAllAdsFiltered.forEach(ad ->{
			QualityAd qualityAd = new QualityAd();
			qualityAd.setDescription(ad.getDescription());
			qualityAd.setGardenSize(ad.getGardenSize());
			qualityAd.setHouseSize(ad.getHouseSize());
			qualityAd.setId(ad.getId());
			qualityAd.setIrrelevantSince(ad.getIrrelevantSince());
			qualityAd.setIrrelevantSinceString(ad.getIrrelevantSince().toString());
			qualityAd.setScore(ad.getScore());
			qualityAd.setTypology(ad.getTypology());
			
			pictureService.getUrlsPictures(ad, urls, pictures);
			
			qualityAd.setPictureUrls(urls);
			result.add(qualityAd);
		});
		
		return result;
	}
	
	@Scheduled(fixedRate = 300000) //chequea cada 5 minutos el estado de los anuncios y sus scores
	//guardando el momento exacto en el que comienzan a ser irrelevantes
	//o reseteando a null para dejar de serlo
	//estos datos se deberían de persisitir en base de datos
	public List<AdVO> checkingIrrelevantAds() {
		List<AdVO> listAllAds = this.getAllScore();
		listAllAds.forEach(ad -> {
			if(ad.getScore() < codingTestRankingConfig.getScoreAdPublic()) {
				ad.setIrrelevantSince(LocalDate.now());
				
			}else {
				ad.setIrrelevantSince(null);
			}
		});
		return listAllAds;
	}
	
}
