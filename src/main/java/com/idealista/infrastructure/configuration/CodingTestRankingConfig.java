package com.idealista.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@RefreshScope //nos da la posibilidad de cambiar en caliente el valor de las propiedades sin necesidad
//de volver a generar la imagen de nuestro ms y tener que desplegarlo de nuevo en el entorno
public class CodingTestRankingConfig{
	
	@Value("${score.ad.without.photo}")
	private Integer scoreAdWithoutPhoto;
	
	@Value("${score.ad.with.photo.HD}")
	private Integer scoreAdWithPhotoHD;
	
	@Value("${score.ad.with.photo.SD}")
	private Integer scoreAdWithPhotoSD;
	
	@Value("${score.ad.with.desc}")
	private Integer scoreAdWithDesc;
	
	//desc between 20 and 49 chars PISOS
	@Value("${score.ad.with.desc.long.type1}")
	private Integer scoreWithDescLongType1;
	
	//#desc with 50 or more chars PISOS
	@Value("${score.ad.with.desc.long.type2}")
	private Integer scoreWithDescLongType2;
	
	//desc with 50 or more chars CHALETS
	@Value("${score.ad.with.desc.long.type3}")
	private Integer scoreWithDescLongType3;
	
	@Value("${score.ad.with.outstanding.words}")
	private Integer scoreAdWithOutstandingWords;

	@Value("${score.ad.complete}")
	private Integer scoreAdComplete;
	
	@Value("${score.ad.min}")
	private Integer scoreAdMin;
	
	@Value("${score.ad.max}")
	private Integer scoreAdMax;
	
	@Value("${score.ad.public}")
	private Integer scoreAdPublic;
}
