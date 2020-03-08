package com.idealista.infrastructure.services;

import java.util.List;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;

public interface AdService {

	public List<AdVO> getAllScore();
	
	public List<PublicAd> getAllPublicAds();
	
	public List<QualityAd> getAllAdsQualityListing();
}
