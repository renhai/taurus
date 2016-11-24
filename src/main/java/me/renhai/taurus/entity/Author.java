package me.renhai.taurus.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Author {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private Integer celebrityId;
	private Integer movieid;
	
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
	public Integer getMovieid() {
		return movieid;
	}
	public void setMovieid(Integer movieid) {
		this.movieid = movieid;
	}
	@Override
	public String toString() {
		return "Author [id=" + id + ", celebrityId=" + celebrityId + ", movieid=" + movieid + "]";
	}
	
}
