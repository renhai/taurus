package me.renhai.taurus.spider.douban;

import java.io.Serializable;

public class DoubanAlbumRating implements Serializable {

	private static final long serialVersionUID = -8237541067646233146L;
	
	private double rating;
	private int ratingSum;
	
	public DoubanAlbumRating(double rating, int ratingSum) {
		this.rating = rating;
		this.ratingSum = ratingSum;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int getRatingSum() {
		return ratingSum;
	}
	public void setRatingSum(int ratingSum) {
		this.ratingSum = ratingSum;
	}
	@Override
	public String toString() {
		return "DoubanAlbumRating [rating=" + rating + ", ratingSum=" + ratingSum + "]";
	}
	
	
}
