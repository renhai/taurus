package me.renhai.taurus.spider.rottentomatoes;

public class RTCriticScore {
	private Integer meterValue;
	private String averageRating;
	private Integer reviewsCounted;
	private Integer fresh;
	private Integer rotten;
	
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
	public Integer getReviewsCounted() {
		return reviewsCounted;
	}
	public void setReviewsCounted(Integer reviewsCounted) {
		this.reviewsCounted = reviewsCounted;
	}
	public Integer getFresh() {
		return fresh;
	}
	public void setFresh(Integer fresh) {
		this.fresh = fresh;
	}
	public Integer getRotten() {
		return rotten;
	}
	public void setRotten(Integer rotten) {
		this.rotten = rotten;
	}
	@Override
	public String toString() {
		return "RTCriticScore [meterValue=" + meterValue + ", averageRating=" + averageRating + ", reviewsCounted="
				+ reviewsCounted + ", fresh=" + fresh + ", rotten=" + rotten + "]";
	}
	
	
	
}
