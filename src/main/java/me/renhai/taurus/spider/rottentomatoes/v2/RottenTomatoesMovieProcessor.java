package me.renhai.taurus.spider.rottentomatoes.v2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class RottenTomatoesMovieProcessor implements PageProcessor {
//	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesMovieProcessor.class);
	private Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL).addOptions(Option.SUPPRESS_EXCEPTIONS);

	private Site site = Site.me().setDomain("rottentomatoes.com").setRetryTimes(5).setTimeOut(10000).setSleepTime(2000).setUserAgent(
	            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;
	     
	@Autowired
	private RottenTomatoesTorturer rottenTomatoesTorturer;
	
	@Override
	public void process(Page page) {
    	if (page.getUrl().get().matches(".*/m/[^/]+/?$")) {
    		rottenTomatoesTorturer.torturePage(page);
    	} else if (page.getUrl().regex(".*/search/\\?search.*").match()) {
    		page.addTargetRequests(page.getHtml().xpath("//section[@id='SummaryResults']/ul/li[1]/div[@class='details']//a[contains(@href, '/m/')][1]").links().all());
    		page.setSkip(true);
    	} else if (page.getUrl().regex(".*/api/private/v1\\.0/search/.*").match()) {
    		String path= JsonPath.using(conf).parse(page.getRawText()).read("$.movies[0].url");
    		if (StringUtils.isNotBlank(path)) {
    			page.addTargetRequest("https://www.rottentomatoes.com" + path);
    		}
    		page.setSkip(true);
    	}
	}

	@Override
	public Site getSite() {
		return site;
	}

}
