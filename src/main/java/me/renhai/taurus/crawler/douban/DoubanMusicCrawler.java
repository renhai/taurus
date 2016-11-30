package me.renhai.taurus.crawler.douban;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import me.renhai.taurus.crawler.AbstractCrawler;

@Service
public class DoubanMusicCrawler extends AbstractCrawler<DoubanAlbum, String> {
	
	private static final Logger LOG = LoggerFactory.getLogger(DoubanMusicCrawler.class);
	
	private By searchResults = By.xpath("//a[@class='nbg' and contains(@href,'subject')]");
	private By showMore = By.xpath("//a[@class='j a_show_full']");
	private By intro = By.xpath("//span[@class='all hidden']");//点击“展开全部”
	private By intro2 = By.xpath("//div[@id='link-report']/span");//没有“展开全部”button
	private By tracks = By.xpath("//h2[contains(text(),'曲目')]/following-sibling::div/div");
	private By tracks2 = By.xpath("//h2[contains(text(),'曲目')]/following-sibling::div/ul/li/div/div/div[contains(@class, 'col song-name-short')]/span");
	
	private By cover = By.xpath("//span[@class='ckd-collect']/a");
	private By name = By.xpath("//div[@id='wrapper']/h1/span");
	private By attrInfo = By.id("info");
	private By rating = By.xpath("//strong[@class='ll rating_num']");
	private By ratingSum = By.xpath("//div[@class='rating_sum']/a/span");
	
	protected DoubanAlbum process(String conditions) {
		List<WebElement> results = getDriver().findElements(searchResults);
		if (CollectionUtils.isEmpty(results)) {
			LOG.warn("NO RESULT RETURN");
			return null;
		}
		WebElement result = results.get(0);
		
		DoubanAlbum albumObj = new DoubanAlbum();
		LOG.info(result.getAttribute("title") + " " + getDriver().getCurrentUrl());
		result.click();
		
		albumObj.setAlbumId(getAlbumId(getDriver().getCurrentUrl()));
		albumObj.setLink(getDriver().getCurrentUrl());
		if (isElementPresent(cover)) {
			albumObj.setCover(getDriver().findElement(cover).getAttribute("href"));
		}
		if (isElementPresent(name)) {
			albumObj.setName(getDriver().findElement(name).getAttribute("innerText"));
		}
		albumObj.setAttrs(getDriver().findElement(attrInfo).getAttribute("innerText"));
		albumObj.setRating(processRating());
		albumObj.setIntro(processIntro());;
		albumObj.setTracks(processTracks());
		
		return albumObj;
	}
	
	private Long getAlbumId(String link) {
		link = StringUtils.trimToEmpty(link);
		link = StringUtils.removeEnd(link, "/");
		if (StringUtils.isEmpty(link)) return null;
		try {
			return Long.parseLong(link.substring(link.lastIndexOf('/') + 1));
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
	}
	
	private DoubanAlbumRating processRating() {
		double rate = 0.0;
		int sum = 0;
		try {
			if (isElementPresent(rating)) {
				String rateStr = getDriver().findElement(rating).getAttribute("innerText");
				if (StringUtils.isNotBlank(rateStr)) {
					rate = Double.parseDouble(rateStr);
				}
			}
			if (isElementPresent(ratingSum)) {
				sum = Integer.parseInt(getDriver().findElement(ratingSum).getAttribute("innerText"));
			}
		} catch (NumberFormatException e) {
			LOG.error(e.getMessage(), e);
		}
		return new DoubanAlbumRating(rate, sum);
	}
	private String processTracks() {
		StringBuilder sb = new StringBuilder();
		List<WebElement> trackList = getDriver().findElements(tracks2);
		if (trackList.size() == 0) {
			trackList = getDriver().findElements(tracks);
		}
		for (WebElement ele : trackList) {
			sb.append(ele.getAttribute("innerText")).append("\n");
		}
		return sb.toString();
	}
	
	private String processIntro() {
		if (isElementPresent(showMore)) {
			getDriver().findElement(showMore).click();
			if (isElementPresent(intro)) {
				return getDriver().findElement(intro).getAttribute("innerText");
			}
		} else {
			if (isElementPresent(intro2)) {
				return getDriver().findElement(intro2).getAttribute("innerText");//textContent, innerHTML
			}
		}
		return StringUtils.EMPTY;
	}
	
	@Override
	protected String getUrl(String conditions) {
		return "https://music.douban.com/subject_search?search_text=" + conditions + "&cat=1003";
	}

}
