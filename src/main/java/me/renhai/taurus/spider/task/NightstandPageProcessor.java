package me.renhai.taurus.spider.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class NightstandPageProcessor implements PageProcessor {
	
	private static final Logger log = LoggerFactory.getLogger(NightstandPageProcessor.class);
	
	private Site site = Site.me().setDomain("wayfair.com").setRetryTimes(3).setTimeOut(10000).setSleepTime(2000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");


	@Override
	public void process(Page page) {
		String price = page.getHtml().xpath("//span[@data-id='dynamic-sku-price']/text()").get();
		page.putField("price", price);
		log.info(price);
	}

	@Override
	public Site getSite() {
		return site;
	}

}
