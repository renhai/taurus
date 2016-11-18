package me.renhai.taurus.spider.rottentomatoes;

import java.util.ArrayList;
import java.util.List;

public class RTMovie {
	private String link;
	private String title;
	private String synopsis;
	private String mpaaRating;
	private String genres;
	private String runtime;
	private Integer year;
	private String criticsConsensus;
	private String inTheatersDate;
	private String onDvdDate;
	private String studio;
	private String directedBy;
	private String writtenBy;
	private String image;
	
	private RTRating rating;
	private List<RTCast> cast = new ArrayList<>();
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
	public String getCriticsConsensus() {
		return criticsConsensus;
	}
	public void setCriticsConsensus(String criticsConsensus) {
		this.criticsConsensus = criticsConsensus;
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
	public String getDirectedBy() {
		return directedBy;
	}
	public void setDirectedBy(String directedBy) {
		this.directedBy = directedBy;
	}
	public String getWrittenBy() {
		return writtenBy;
	}
	public void setWrittenBy(String writtenBy) {
		this.writtenBy = writtenBy;
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
	@Override
	public String toString() {
		return "RTMovie [link=" + link + ", title=" + title + ", synopsis=" + synopsis + ", mpaaRating=" + mpaaRating
				+ ", genres=" + genres + ", runtime=" + runtime + ", year=" + year + ", criticsConsensus="
				+ criticsConsensus + ", inTheatersDate=" + inTheatersDate + ", onDvdDate=" + onDvdDate + ", studio="
				+ studio + ", directedBy=" + directedBy + ", writtenBy=" + writtenBy + ", image=" + image + ", rating="
				+ rating + ", cast=" + cast + "]";
	}
	
	
}
