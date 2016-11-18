package me.renhai.taurus.spider.rottentomatoes;

public class RTRating {
	
	private RTCriticScore criticScore;
	private RTAudienceScore audienceScore;
	public RTCriticScore getCriticScore() {
		return criticScore;
	}
	public void setCriticScore(RTCriticScore criticScore) {
		this.criticScore = criticScore;
	}
	public RTAudienceScore getAudienceScore() {
		return audienceScore;
	}
	public void setAudienceScore(RTAudienceScore audienceScore) {
		this.audienceScore = audienceScore;
	}
	@Override
	public String toString() {
		return "RTRating [criticScore=" + criticScore + ", audienceScore=" + audienceScore + "]";
	}

	
}
