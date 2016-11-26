package me.renhai.taurus.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(
indexes = {
	@Index (name = "idx_criticratingvalue_rating", columnList = "criticRatingValue"),
})
public class Rating implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2470764478958994983L;

	@Id
    @OneToOne
    @JoinColumn(name = "movie_id")
	@JsonIgnore
	private Movie movie;
	
	@Column(length = 1024)
	private String criticsConsensus;
	private Integer criticRatingValue;
	private String criticAverageRating;
	private Integer criticReviewsCounted;
	private Integer criticFresh;
	private Integer criticRotten;
	
	private Integer audienceRatingValue;
	private String audienceAverageRating;
	private Integer audienceRatingCount;
	
	public String getCriticsConsensus() {
		return criticsConsensus;
	}
	public void setCriticsConsensus(String criticsConsensus) {
		this.criticsConsensus = criticsConsensus;
	}
	public Integer getCriticRatingValue() {
		return criticRatingValue;
	}
	public void setCriticRatingValue(Integer criticRatingValue) {
		this.criticRatingValue = criticRatingValue;
	}
	public String getCriticAverageRating() {
		return criticAverageRating;
	}
	public void setCriticAverageRating(String criticAverageRating) {
		this.criticAverageRating = criticAverageRating;
	}
	public Integer getCriticReviewsCounted() {
		return criticReviewsCounted;
	}
	public void setCriticReviewsCounted(Integer criticReviewsCounted) {
		this.criticReviewsCounted = criticReviewsCounted;
	}
	public Integer getCriticFresh() {
		return criticFresh;
	}
	public void setCriticFresh(Integer criticFresh) {
		this.criticFresh = criticFresh;
	}
	public Integer getCriticRotten() {
		return criticRotten;
	}
	public void setCriticRotten(Integer criticRotten) {
		this.criticRotten = criticRotten;
	}
	public Integer getAudienceRatingValue() {
		return audienceRatingValue;
	}
	public void setAudienceRatingValue(Integer audienceRatingValue) {
		this.audienceRatingValue = audienceRatingValue;
	}
	public String getAudienceAverageRating() {
		return audienceAverageRating;
	}
	public void setAudienceAverageRating(String audienceAverageRating) {
		this.audienceAverageRating = audienceAverageRating;
	}
	public Integer getAudienceRatingCount() {
		return audienceRatingCount;
	}
	public void setAudienceRatingCount(Integer audienceRatingCount) {
		this.audienceRatingCount = audienceRatingCount;
	}

	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
}
