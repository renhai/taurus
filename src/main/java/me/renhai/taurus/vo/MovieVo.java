package me.renhai.taurus.vo;

import java.util.Date;
import java.util.List;

public class MovieVo {
	private Integer id;
	private String outerId;
	private Integer source;
	private String link;
	private String title;
	private String synopsis;
	private String mpaaRating;
	private String genres;
	private Integer runtime;
	private Integer year;
	private Date inTheatersDate;
	private Date onDvdDate;
	private String studio;
	private String image;
	private RatingVo rating;
	private List<CastingItem> castingItems;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOuterId() {
		return outerId;
	}
	public void setOuterId(String outerId) {
		this.outerId = outerId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getMpaaRating() {
		return mpaaRating;
	}
	public void setMpaaRating(String mpaaRating) {
		this.mpaaRating = mpaaRating;
	}
	public String getGenres() {
		return genres;
	}
	public void setGenres(String genres) {
		this.genres = genres;
	}
	public Integer getRuntime() {
		return runtime;
	}
	public void setRuntime(Integer runtime) {
		this.runtime = runtime;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Date getInTheatersDate() {
		return inTheatersDate;
	}
	public void setInTheatersDate(Date inTheatersDate) {
		this.inTheatersDate = inTheatersDate;
	}
	public Date getOnDvdDate() {
		return onDvdDate;
	}
	public void setOnDvdDate(Date onDvdDate) {
		this.onDvdDate = onDvdDate;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public RatingVo getRating() {
		return rating;
	}
	public void setRating(RatingVo rating) {
		this.rating = rating;
	}
	public List<CastingItem> getCastingItems() {
		return castingItems;
	}
	public void setCastingItems(List<CastingItem> castingItems) {
		this.castingItems = castingItems;
	}
	
}
