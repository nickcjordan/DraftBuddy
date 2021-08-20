
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model.ffballers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "created_at",
    "updated_at",
    "player_id",
    "blurb",
    "video",
    "dynasty_blurb"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerInfo {

    @JsonProperty("id")
    private String id;
    @JsonProperty("created_at")
    private Object createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("player_id")
    private String playerId;
    @JsonProperty("blurb")
    private String blurb;
    @JsonProperty("video")
    private Object video;
    @JsonProperty("dynasty_blurb")
    private String dynastyBlurb;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("created_at")
    public Object getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("player_id")
    public String getPlayerId() {
        return playerId;
    }

    @JsonProperty("player_id")
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @JsonProperty("blurb")
    public String getBlurb() {
        return blurb;
    }

    @JsonProperty("blurb")
    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    @JsonProperty("video")
    public Object getVideo() {
        return video;
    }

    @JsonProperty("video")
    public void setVideo(Object video) {
        this.video = video;
    }

    @JsonProperty("dynasty_blurb")
    public String getDynastyBlurb() {
        return dynastyBlurb;
    }

    @JsonProperty("dynasty_blurb")
    public void setDynastyBlurb(String dynastyBlurb) {
        this.dynastyBlurb = dynastyBlurb;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PlayerInfo .class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("updatedAt");
        sb.append('=');
        sb.append(((this.updatedAt == null)?"<null>":this.updatedAt));
        sb.append(',');
        sb.append("playerId");
        sb.append('=');
        sb.append(((this.playerId == null)?"<null>":this.playerId));
        sb.append(',');
        sb.append("blurb");
        sb.append('=');
        sb.append(((this.blurb == null)?"<null>":this.blurb));
        sb.append(',');
        sb.append("video");
        sb.append('=');
        sb.append(((this.video == null)?"<null>":this.video));
        sb.append(',');
        sb.append("dynastyBlurb");
        sb.append('=');
        sb.append(((this.dynastyBlurb == null)?"<null>":this.dynastyBlurb));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
