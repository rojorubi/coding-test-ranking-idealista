package com.idealista.infrastructure.mappers;

import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.stereotype.Service;

@Service(value = "templateMapper")
public class CodingTestRankingMapperImpl implements CodingTestRankingMapper {

	@Override
	public BeanMappingBuilder getMappingBuilder() {
		
		return new BeanMappingBuilder() {
			
			protected void configure() {
			//	mapping(XXX.class, XXX.class);
			}
		};
		
	}
	    
}