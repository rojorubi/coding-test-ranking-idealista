package com.idealista.infrastructure.api;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class QualityAd {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;
    private Integer score;
    private LocalDate irrelevantSince;
    private String irrelevantSinceString;
    
}
