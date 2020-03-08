package com.idealista.infrastructure.utils.constants;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum EnumQualityPicture {
	
	HD("HD"),
	SD("SD");
	
	private String value;
	
	public static List<String> getListEnumQualityPicture(){
		List<String> listOperationAd= new ArrayList<>();
		int count=0;		
		EnumQualityPicture[] values = values();		
		while (count<values.length){
			listOperationAd.add(values[count].getValue());
			count++;
		}		
		return listOperationAd;
	}
}
