package me.renhai.taurus.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"movieId"})
})
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer movieId;
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
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
	@Override
	public String toString() {
		return "Rating [id=" + id + ", movieId=" + movieId + ", criticsConsensus=" + criticsConsensus
				+ ", criticRatingValue=" + criticRatingValue + ", criticAverageRating=" + criticAverageRating
				+ ", criticReviewsCounted=" + criticReviewsCounted + ", criticFresh=" + criticFresh + ", criticRotten="
				+ criticRotten + ", audienceRatingValue=" + audienceRatingValue + ", audienceAverageRating="
				+ audienceAverageRating + ", audienceRatingCount=" + audienceRatingCount + "]";
	}

	
	
}
