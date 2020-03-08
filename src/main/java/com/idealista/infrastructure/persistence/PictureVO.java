package com.idealista.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureVO {

    private Integer id;
    private String url;
    private String quality;
    
}
