package me.renhai.taurus.spider.rottentomatoes;

public class RTCast {
	private String name;
	private String characters;
	private String link;
	private String image;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCharacters() {
		return characters;
	}
	public void setCharacters(String characters) {
		this.characters = characters;
	}
	@Override
	public String toString() {
		return "RTCast [name=" + name + ", characters=" + characters + ", link=" + link + ", image=" + image + "]";
	}
	
	
}
