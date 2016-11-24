package me.renhai.taurus.entity;

public class Casting {
	private Integer id;
	private Integer movieId;
	private Integer celebrityId;
	private String characters;
	
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
	public Integer getCelebrityId() {
		return celebrityId;
	}
	public void setCelebrityId(Integer celebrityId) {
		this.celebrityId = celebrityId;
	}
	public String getCharacters() {
		return characters;
	}
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	@Override
	public String toString() {
		return "Casting [id=" + id + ", movieId=" + movieId + ", celebrityId=" + celebrityId + ", characters="
				+ characters + "]";
	}
	
	
}
