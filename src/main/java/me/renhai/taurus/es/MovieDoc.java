package me.renhai.taurus.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "taurus", type = "movie", shards = 1, replicas = 0)
public class MovieDoc {
	@Id
	private String id;
	@Field(type = FieldType.String)
	private String name;
	@Field(type = FieldType.Integer)
	private Integer year;
	@Field(type = FieldType.Integer)
	private Integer source;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", year=" + year + ", source=" + source + "]";
	}
	
}
