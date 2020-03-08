package com.idealista.infrastructure.persistence;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Equivalent to @Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode.
@NoArgsConstructor
@AllArgsConstructor
public class AdVO {

	@NotNull
    private Integer id;
	
	@NotNull
	private String typology;
    
	private String description;
    
    private List<Integer> pictures;
    
	@NotNull
	private Integer houseSize;
	
	private Integer gardenSize;
    
	private Integer score;
    
	private LocalDate irrelevantSince;
    
}
