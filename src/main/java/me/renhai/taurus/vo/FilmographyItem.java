package me.renhai.taurus.vo;

public class FilmographyItem {
	private Integer movieId;
	private String movieTitle;
	private Integer movieYear;
	private String celebrityName;
	private Integer criticRatingValue;
	
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	public String getMovieTitle() {
		return movieTitle;
	}
	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	public Integer getMovieYear() {
		return movieYear;
	}
	public void setMovieYear(Integer movieYear) {
		this.movieYear = movieYear;
	}
	public String getCelebrityName() {
		return celebrityName;
	}
	public void setCelebrityName(String celebrityName) {
		this.celebrityName = celebrityName;
	}
	public Integer getCriticRatingValue() {
		return criticRatingValue;
	}
	public void setCriticRatingValue(Integer criticRatingValue) {
		this.criticRatingValue = criticRatingValue;
	}
	
	
}
