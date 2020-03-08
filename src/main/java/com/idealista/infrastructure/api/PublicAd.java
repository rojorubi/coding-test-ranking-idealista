package com.idealista.infrastructure.api;

import java.util.List;

import lombok.Data;

@Data
public class PublicAd {

    private Integer id;
    private String typology;
    private String description;
    private List<String> pictureUrls;
    private Integer houseSize;
    private Integer gardenSize;

}
