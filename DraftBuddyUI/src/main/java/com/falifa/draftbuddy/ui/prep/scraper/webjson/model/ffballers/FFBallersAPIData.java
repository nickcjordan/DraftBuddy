
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FFBallersAPIData {

    private HashMap<String, PlayerInfo> essentials;
    private List<Projection> projections;

    public HashMap<String, PlayerInfo> getEssentials() {
		return essentials;
	}

	public void setEssentials(HashMap<String, PlayerInfo> essentials) {
		this.essentials = essentials;
	}

	@JsonProperty("projections")
    public List<Projection> getProjections() {
        return projections;
    }

    @JsonProperty("projections")
    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

}
