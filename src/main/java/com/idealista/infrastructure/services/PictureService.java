package com.idealista.infrastructure.services;

import java.util.List;

import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.persistence.PictureVO;

public interface PictureService {

	public AdVO getScoreByPhoto(AdVO ad, List<PictureVO> pictures);
		
	public Integer getScoreByPicture(Integer id, List<PictureVO> pictures) throws Exception;
	
	public void getUrlsPictures(AdVO ad, List<String> urls, List<PictureVO> pictures);
		
}
