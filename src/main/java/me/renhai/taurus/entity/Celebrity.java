package me.renhai.taurus.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Table(
indexes = {
	@Index (name = "idx_name_celebrity", columnList = "name")
}, 
uniqueConstraints = {
	@UniqueConstraint(name = "uk_link_celebrity", columnNames = {"link"}),
	@UniqueConstraint(name = "uk_source_actorid_celebrity", columnNames = {"source", "actorId"}),
})
@Entity
@Indexed
public class Celebrity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2344535958387644946L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String actorId;
	@NotNull
	private Integer source;
	@Column(length = 25)
	private String type;
	@Field
	@Analyzer(definition = "en")
	@Column(length = 128)
	@NotNull
	private String name;
	@Column(length = 128)
	@NotNull
	private String link;
	@Column(length = 255)
	private String image;
	private Date birthday;
	@Column(length = 128)
	private String birthplace;
	@Lob
	private String bio;
	private Long createTime;
	private Long updateTime;

	
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
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
}
