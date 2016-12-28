package me.renhai.taurus.vo;

import java.util.Date;
import java.util.List;

public class CelebrityVo {
	private Integer id;
	private String actorId;
	private Integer source;
	private String type;
	private String name;
	private String link;
	private String image;
	private Date birthday;
	private String birthplace;
	private String bio;
	private List<FilmographyItem> filmographyItems;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getBirthplace() {
		return birthplace;
	}
	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<FilmographyItem> getFilmographyItems() {
		return filmographyItems;
	}
	public void setFilmographyItems(List<FilmographyItem> filmographyItems) {
		this.filmographyItems = filmographyItems;
	}
	
	
}
