package me.renhai.taurus.spider.rottentomatoes;

import java.util.ArrayList;
import java.util.List;

import net.minidev.json.JSONArray;

public class RTMovie {
	private Long movieId;
	private String link;
	private String title;
	private String synopsis;
	private String mpaaRating;
	private String genres;
	private String runtime;
	private Integer year;
	private String inTheatersDate;
	private String onDvdDate;
	private String studio;
	private JSONArray directors;
	private JSONArray authors;
	private String image;
	private Long timestamp;
	
	private RTRating rating;
	private List<RTCast> cast = new ArrayList<>();
	
	public Long getMovieId() {
		return movieId;
	}
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
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
	public String getRuntime() {
		return runtime;
	}
	public void setRuntime(String runtime) {
		this.runtime = runtime;
	}

	public String getInTheatersDate() {
		return inTheatersDate;
	}
	public void setInTheatersDate(String inTheatersDate) {
		this.inTheatersDate = inTheatersDate;
	}
	public String getOnDvdDate() {
		return onDvdDate;
	}
	public void setOnDvdDate(String onDvdDate) {
		this.onDvdDate = onDvdDate;
	}
	public String getStudio() {
		return studio;
	}
	public void setStudio(String studio) {
		this.studio = studio;
	}

	public RTRating getRating() {
		return rating;
	}
	public void setRating(RTRating rating) {
		this.rating = rating;
	}
	public List<RTCast> getCast() {
		return cast;
	}
	public void setCast(List<RTCast> cast) {
		this.cast = cast;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public JSONArray getDirectors() {
		return directors;
	}
	public void setDirectors(JSONArray directors) {
		this.directors = directors;
	}
	public JSONArray getAuthors() {
		return authors;
	}
	public void setAuthors(JSONArray authors) {
		this.authors = authors;
	}
	@Override
	public String toString() {
		return "RTMovie [movieId=" + movieId + ", link=" + link + ", title=" + title + ", synopsis=" + synopsis
				+ ", mpaaRating=" + mpaaRating + ", genres=" + genres + ", runtime=" + runtime + ", year=" + year
				+ ", inTheatersDate=" + inTheatersDate + ", onDvdDate=" + onDvdDate + ", studio=" + studio
				+ ", directors=" + directors + ", authors=" + authors + ", image=" + image + ", timestamp=" + timestamp
				+ ", rating=" + rating + ", cast=" + cast + "]";
	}
	
	
}
