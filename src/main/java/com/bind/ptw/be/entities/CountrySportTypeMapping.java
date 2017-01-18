package com.bind.ptw.be.entities;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "COUNTRY_SPORT_TYPE_MAPPING")
public class CountrySportTypeMapping {
	
	@EmbeddedId
	private CountrySportTypeMappingKey countrySportTypeMappingKey;

	public CountrySportTypeMappingKey getCountrySportTypeMappingKey() {
		return countrySportTypeMappingKey;
	}

	public void setCountrySportTypeMappingKey(CountrySportTypeMappingKey countrySportTypeMappingKey) {
		this.countrySportTypeMappingKey = countrySportTypeMappingKey;
	}
	
	
	
	
}
