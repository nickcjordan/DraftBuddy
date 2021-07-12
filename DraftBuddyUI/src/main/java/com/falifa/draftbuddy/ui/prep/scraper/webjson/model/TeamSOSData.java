
package com.falifa.draftbuddy.ui.prep.scraper.webjson.model;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

public class TeamSOSData {

    @JsonProperty("games")
    private Integer games;
    @JsonProperty("qb_stars")
    private Double qbStars;
    @JsonProperty("qb_rank")
    private Integer qbRank;
    @JsonProperty("rb_stars")
    private Double rbStars;
    @JsonProperty("rb_rank")
    private Integer rbRank;
    @JsonProperty("wr_stars")
    private Double wrStars;
    @JsonProperty("wr_rank")
    private Integer wrRank;
    @JsonProperty("te_stars")
    private Double teStars;
    @JsonProperty("te_rank")
    private Integer teRank;
    @JsonProperty("k_stars")
    private Double kStars;
    @JsonProperty("k_rank")
    private Integer kRank;
    @JsonProperty("dst_stars")
    private Double dstStars;
    @JsonProperty("dst_rank")
    private Integer dstRank;

    @JsonProperty("games")
    public Integer getGames() {
        return games;
    }

    @JsonProperty("games")
    public void setGames(Integer games) {
        this.games = games;
    }

    @JsonProperty("qb_stars")
    public Double getQbStars() {
        return qbStars;
    }

    @JsonProperty("qb_stars")
    public void setQbStars(Double qbStars) {
        this.qbStars = qbStars;
    }

    @JsonProperty("qb_rank")
    public Integer getQbRank() {
        return qbRank;
    }

    @JsonProperty("qb_rank")
    public void setQbRank(Integer qbRank) {
        this.qbRank = qbRank;
    }

    @JsonProperty("rb_stars")
    public Double getRbStars() {
        return rbStars;
    }

    @JsonProperty("rb_stars")
    public void setRbStars(Double rbStars) {
        this.rbStars = rbStars;
    }

    @JsonProperty("rb_rank")
    public Integer getRbRank() {
        return rbRank;
    }

    @JsonProperty("rb_rank")
    public void setRbRank(Integer rbRank) {
        this.rbRank = rbRank;
    }

    @JsonProperty("wr_stars")
    public Double getWrStars() {
        return wrStars;
    }

    @JsonProperty("wr_stars")
    public void setWrStars(Double wrStars) {
        this.wrStars = wrStars;
    }

    @JsonProperty("wr_rank")
    public Integer getWrRank() {
        return wrRank;
    }

    @JsonProperty("wr_rank")
    public void setWrRank(Integer wrRank) {
        this.wrRank = wrRank;
    }

    @JsonProperty("te_stars")
    public Double getTeStars() {
        return teStars;
    }

    @JsonProperty("te_stars")
    public void setTeStars(Double teStars) {
        this.teStars = teStars;
    }

    @JsonProperty("te_rank")
    public Integer getTeRank() {
        return teRank;
    }

    @JsonProperty("te_rank")
    public void setTeRank(Integer teRank) {
        this.teRank = teRank;
    }

    @JsonProperty("k_stars")
    public Double getkStars() {
        return kStars;
    }

    @JsonProperty("k_stars")
    public void setkStars(Double kStars) {
        this.kStars = kStars;
    }

    @JsonProperty("k_rank")
    public Integer getkRank() {
        return kRank;
    }

    @JsonProperty("k_rank")
    public void setkRank(Integer kRank) {
        this.kRank = kRank;
    }

    @JsonProperty("dst_stars")
    public Double getDstStars() {
        return dstStars;
    }

    @JsonProperty("dst_stars")
    public void setDstStars(Double dstStars) {
        this.dstStars = dstStars;
    }

    @JsonProperty("dst_rank")
    public Integer getDstRank() {
        return dstRank;
    }

    @JsonProperty("dst_rank")
    public void setDstRank(Integer dstRank) {
        this.dstRank = dstRank;
    }

}
