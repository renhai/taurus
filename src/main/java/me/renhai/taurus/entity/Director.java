package me.renhai.taurus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Director {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer celebrityId;
	private Integer movieId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCelebrityId() {
		return celebrityId;
	}
	public void setCelebrityId(Integer celebrityId) {
		this.celebrityId = celebrityId;
	}
	public Integer getMovieId() {
		return movieId;
	}
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	@Override
	public String toString() {
		return "Director [id=" + id + ", celebrityId=" + celebrityId + ", movieId=" + movieId + "]";
	}
	
	
}
