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

@Entity
@Table(
indexes = {
	@Index (columnList = "year"),
	@Index (columnList = "title")
}, 
uniqueConstraints = {
	@UniqueConstraint(columnNames = {"outerId", "source"}),
	@UniqueConstraint(columnNames = {"link"})
})
public class Movie {
	public enum Source {
		ROTTEN_TOMATOES(1),	
		IMDB(2);
		
		private int code;
		Source(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
	} 
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String outerId;
	private Integer source;
	private String link;
	private String title;
	@Lob
	private String synopsis;
	private String mpaaRating;
	private String genres;
	private Integer runtime;
	private Integer year;
	private Date inTheatersDate;
	private Date onDvdDate;
	private String studio;
	private String image;
	private Long timestamp;
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
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", outerId=" + outerId + ", source=" + source + ", link=" + link + ", title=" + title
				+ ", synopsis=" + synopsis + ", mpaaRating=" + mpaaRating + ", genres=" + genres + ", runtime="
				+ runtime + ", year=" + year + ", inTheatersDate=" + inTheatersDate + ", onDvdDate=" + onDvdDate
				+ ", studio=" + studio + ", image=" + image + ", timestamp=" + timestamp + "]";
	}
	
	
}
