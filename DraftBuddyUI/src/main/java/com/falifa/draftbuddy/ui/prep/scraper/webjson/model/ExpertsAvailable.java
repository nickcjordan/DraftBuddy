
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "included",
    "excluded",
    "last_update"
})
@Generated("jsonschema2pojo")
public class ExpertsAvailable {

    @JsonProperty("total")
    private Integer total;
    @JsonProperty("included")
    private List<Integer> included = null;
    @JsonProperty("excluded")
    private List<Integer> excluded = null;
    @JsonProperty("last_update")
    private Integer lastUpdate;

    @JsonProperty("total")
    public Integer getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(Integer total) {
        this.total = total;
    }

    @JsonProperty("included")
    public List<Integer> getIncluded() {
        return included;
    }

    @JsonProperty("included")
    public void setIncluded(List<Integer> included) {
        this.included = included;
    }

    @JsonProperty("excluded")
    public List<Integer> getExcluded() {
        return excluded;
    }

    @JsonProperty("excluded")
    public void setExcluded(List<Integer> excluded) {
        this.excluded = excluded;
    }

    @JsonProperty("last_update")
    public Integer getLastUpdate() {
        return lastUpdate;
    }

    @JsonProperty("last_update")
    public void setLastUpdate(Integer lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ExpertsAvailable.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("total");
        sb.append('=');
        sb.append(((this.total == null)?"<null>":this.total));
        sb.append(',');
        sb.append("included");
        sb.append('=');
        sb.append(((this.included == null)?"<null>":this.included));
        sb.append(',');
        sb.append("excluded");
        sb.append('=');
        sb.append(((this.excluded == null)?"<null>":this.excluded));
        sb.append(',');
        sb.append("lastUpdate");
        sb.append('=');
        sb.append(((this.lastUpdate == null)?"<null>":this.lastUpdate));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
