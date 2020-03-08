package com.idealista.infrastructure.utils.constants;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum EnumOutstandingWord {
	
	LUMINOSO("LUMINOSO"),
	NUEVO("NUEVO"),
	CENTRICO("CENTRICO"),
	REFORMADO("REFORMADO"),
	ATICO("ATICO");
	
	private String value;
	
	public static List<String> getListEnumOutstandingWord(){
		List<String> listOperationAd= new ArrayList<>();
		int count=0;		
		EnumOutstandingWord[] values = values();		
		while (count<values.length){
			listOperationAd.add(values[count].getValue());
			count++;
		}		
		return listOperationAd;
	}
}
