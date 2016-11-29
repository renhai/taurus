package me.renhai.taurus.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Table(
indexes = {
	@Index (name = "idx_year_movie", columnList = "year"),
	@Index (name = "idx_title_movie", columnList = "title")
}, 
uniqueConstraints = {
	@UniqueConstraint(name = "uk_outerid_source_movie", columnNames = {"outerId", "source"}),
	@UniqueConstraint(name = "uk_link_movie", columnNames = {"link"})
})
@Indexed
@AnalyzerDefs(
		@AnalyzerDef(
				name = "en", 
				tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class), 
				filters = {
						@TokenFilterDef(factory = LowerCaseFilterFactory.class),
						@TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {@Parameter(name = "language", value = "English") }), 
//						@TokenFilterDef(factory = NGramFilterFactory.class, params = {@Parameter(name = "minGramSize", value = "3"), @Parameter(name = "maxGramSize", value = "3")}), 
						})
		)
public class Movie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2563824754979383249L;
	
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
	
	@NotNull
	@Column(length = 60)
	private String outerId;
	
	@NotNull
	private Integer source;
	
	@Column(length = 255)
	private String link;
	
	@Field(index = org.hibernate.search.annotations.Index.YES, store = Store.NO, analyze = Analyze.YES)
	@Analyzer(definition = "en")
	@NotNull
	@Boost(2.0f)
	@Column(length = 255)
	private String title;
	
	@Lob
	@Field
	@Analyzer(definition = "en")
	private String synopsis;
	
	@Column(length = 128)
	private String mpaaRating;
	@Column(length = 128)
	private String genres;
	private Integer runtime;
	private Integer year;
	private Date inTheatersDate;
	private Date onDvdDate;
	@Column(length = 128)
	private String studio;
	@Column(length = 255)
	private String image;
	private Long timestamp;
	private Long createTime;
	private Long updateTime;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "movie")
	private Rating rating;
	
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
	public Rating getRating() {
		return rating;
	}
	public void setRating(Rating rating) {
		this.rating = rating;
	}
	
}
