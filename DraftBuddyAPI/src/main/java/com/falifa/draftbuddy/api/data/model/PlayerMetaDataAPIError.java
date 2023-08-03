package com.falifa.draftbuddy.api.data.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerMetaDataAPIError {
	
	private List<PlayerMetaDataAPIErrorDetail> errors;

	public List<PlayerMetaDataAPIErrorDetail> getErrors() {
		return errors;
	}

	public void setErrors(List<PlayerMetaDataAPIErrorDetail> errors) {
		this.errors = errors;
	}
	
}
