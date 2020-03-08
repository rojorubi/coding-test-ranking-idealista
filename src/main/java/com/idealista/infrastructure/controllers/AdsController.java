package com.idealista.infrastructure.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.idealista.infrastructure.api.PublicAd;
import com.idealista.infrastructure.api.QualityAd;
import com.idealista.infrastructure.persistence.AdVO;
import com.idealista.infrastructure.services.AdService;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AdsController {

	@Autowired
	private AdService adService;
	
	@ApiOperation(value = "Get and list all ads for a department manager")
	@GetMapping(value = "/getAllQualitingListing", produces = "application/json")
    public ResponseEntity<List<QualityAd>> qualityListing() {
        log.info("Ini endpoint qualityListing");
    	
    	List<QualityAd> listResult = adService.getAllAdsQualityListing();
    	
    	return new ResponseEntity<>(listResult, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get all Ads for a public user")
	@GetMapping(value = "/getAllPublicAds", produces = "application/json")
    public ResponseEntity<List<PublicAd>> getAllPublicAds() {
    	log.info("Ini endpoint getAllPublicAds");
    	
    	List<PublicAd> listResult = adService.getAllPublicAds();
    	
    	return new ResponseEntity<>(listResult, HttpStatus.OK);
    }
    
    @ApiOperation(value = "Get score for all Ads")
	@GetMapping(value = "/getScoreForAllAds", produces = "application/json")	
    public ResponseEntity<List<AdVO>> calculateScore() {
    	
    	log.info("Ini endpoint getScoreForAllAds");
    	List<AdVO> listResult = adService.getAllScore();
    	
    	return new ResponseEntity<>(listResult, HttpStatus.OK);
    }
}
