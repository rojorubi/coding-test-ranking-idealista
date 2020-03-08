package com.idealista.infrastructure.utils.constants;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum EnumTipology {
	
	CHALET("CHALET"),
	PISO("FLAT"),
	GARAJE("GARAJE");
	
	private String value;
	
	public static List<String> getListEnumTipology(){
		List<String> listOperationAd= new ArrayList<>();
		int count=0;		
		EnumTipology[] values = values();		
		while (count<values.length){
			listOperationAd.add(values[count].getValue());
			count++;
		}		
		return listOperationAd;
	}
}
