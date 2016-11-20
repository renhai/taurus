package me.renhai.taurus.spider.rottentomatoes;

import org.springframework.stereotype.Service;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
//@Scope("prototype")
public class RottenTomatoesMovieProcessor implements PageProcessor {

	private Site site = Site.me().setDomain("rottentomatoes.com").setRetryTimes(5).setTimeOut(10000).setSleepTime(2000).setUserAgent(
	            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;
	     
	public RottenTomatoesMovieProcessor()  {
		System.out.println("RottenTomatoesMovieProcessor init");
	}
	@Override
	public void process(Page page) {
    	if (page.getUrl().regex(".*/m/.*").match()) {
    		page.putField("link", page.getUrl().toString());
    		page.putField("script", page.getHtml().xpath("//script[@id='jsonLdSchema']/html()").get());
    		page.putField("title", page.getHtml().xpath("//div[@id='heroImageContainer']//h1/text()").get());
    		page.putField("movieSynopsis", page.getHtml().xpath("//div[@id='movieSynopsis']/text()").get());
    	} else if (page.getUrl().regex(".*/search/\\?search.*").match()) {
    		page.addTargetRequests(page.getHtml().xpath("//section[@id='SummaryResults']/ul/li[1]/div[@class='details']//a[contains(@href, '/m/')][1]").links().all());
    		page.setSkip(true);
    	}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
