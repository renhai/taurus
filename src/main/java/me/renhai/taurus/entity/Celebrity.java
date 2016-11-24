package me.renhai.taurus.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(
indexes = {
	@Index (columnList = "name")
}, 
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"actorId", "source"}),
	@UniqueConstraint(columnNames = {"link"})
})
@Entity
public class Celebrity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String actorId;
	private Integer source;
	private String type;
	private String name;
	private String link;
	private String image;
	private Date birthday;
	private String birthplace;
	@Lob
	private String bio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getActorId() {
		return actorId;
	}
	public void setActorId(String actorId) {
		this.actorId = actorId;
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
	@Override
	public String toString() {
		return "Celebrity [id=" + id + ", source=" + source + ", actorId=" + actorId + ", type=" + type + ", name="
				+ name + ", link=" + link + ", image=" + image + ", birthday=" + birthday + ", birthplace=" + birthplace
				+ ", bio=" + bio + "]";
	}
	
}
