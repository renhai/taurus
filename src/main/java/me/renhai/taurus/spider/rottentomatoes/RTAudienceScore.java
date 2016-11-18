package me.renhai.taurus.spider.rottentomatoes;

public class RTAudienceScore {
	private Integer meterValue;
	private String averageRating;
	private Integer userRatings;
	public Integer getMeterValue() {
		return meterValue;
	}
	public void setMeterValue(Integer meterValue) {
		this.meterValue = meterValue;
	}
	public String getAverageRating() {
		return averageRating;
	}
	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}
	public Integer getUserRatings() {
		return userRatings;
	}
	public void setUserRatings(Integer userRatings) {
		this.userRatings = userRatings;
	}
	@Override
	public String toString() {
		return "RTAudienceScore [meterValue=" + meterValue + ", averageRating=" + averageRating + ", userRatings="
				+ userRatings + "]";
	}
	
}
