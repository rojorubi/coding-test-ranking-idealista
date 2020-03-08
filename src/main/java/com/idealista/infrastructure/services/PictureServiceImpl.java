package com.idealista.infrastructure.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idealista.infrastructure.configuration.CodingTestRankingConfig;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.PictureVO;
import com.idealista.infrastructure.utils.constants.EnumQualityPicture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PictureServiceImpl implements PictureService{

	@Autowired
	private CodingTestRankingConfig codingTestRankingConfig;
	
	public AdVO getScoreByPhoto(AdVO ad, List<PictureVO> pictures) {
		
		if(ad.getPictures().isEmpty()){		
			ad.setScore(ad.getScore() - codingTestRankingConfig.getScoreAdWithoutPhoto());
		}else {
			ad.getPictures().stream().forEach(idPicture -> {
				log.info("\t Id picture: "+idPicture);
				try {
					ad.setScore(ad.getScore() + this.getScoreByPicture(idPicture, pictures));
				} catch (Exception e) {
					log.info(e.getMessage());
				}
			});
		}
		return ad;
	}
	
	public Integer getScoreByPicture(Integer id, List<PictureVO> pictures) throws Exception {
		Integer result =0;
		
		List<PictureVO> picture = pictures.stream().filter(p -> p.getId()==id).collect(Collectors.toList());
		if(picture.size()>1) {
			new Exception("Error, we have two pictures with same identifier.");
			log.error("Error, we have two pictures with same identifier.");
		}else if(picture.isEmpty()) {
			new Exception("Error, the picture id ("+id+") doesn't exist in database");
			log.error("Error, the picture id ("+id+") doesn't exist in database");
		}else {
			
			if(picture.get(0).getQuality().equalsIgnoreCase(EnumQualityPicture.HD.getValue())) {
				log.info("\t \t Set score by HD quality " + codingTestRankingConfig.getScoreAdWithPhotoHD());
				result += codingTestRankingConfig.getScoreAdWithPhotoHD();
			}else if(picture.get(0).getQuality().equalsIgnoreCase(EnumQualityPicture.SD.getValue())) {
				log.info("\t \t Set score by SD quality " + codingTestRankingConfig.getScoreAdWithPhotoSD());
				result += codingTestRankingConfig.getScoreAdWithPhotoSD();
			}
		}
		return result;
	}
	
	public void getUrlsPictures(AdVO ad, List<String> urls, List<PictureVO> pictures) {
		ad.getPictures().forEach(idPicture -> {
			Optional<PictureVO> pictureVO = pictures.stream().
				    filter(p -> p.getId()==idPicture).
				    findFirst();
			if(pictureVO.isPresent()) {
				log.info("\t\t url added: "+pictureVO.get().getUrl());
				urls.add(pictureVO.get().getUrl());
			}			
		});
	}
}
