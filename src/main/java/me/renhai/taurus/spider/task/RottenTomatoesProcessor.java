package me.renhai.taurus.spider.task;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;

import me.renhai.taurus.spider.rottentomatoes.RTAudienceScore;
import me.renhai.taurus.spider.rottentomatoes.RTCast;
import me.renhai.taurus.spider.rottentomatoes.RTCriticScore;
import me.renhai.taurus.spider.rottentomatoes.RTRating;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

public class RottenTomatoesProcessor implements PageProcessor {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesProcessor.class);
	private Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL)
			.addOptions(Option.SUPPRESS_EXCEPTIONS);

	private Site site = Site.me().setDomain("rottentomatoes.com").setRetryTimes(5).setTimeOut(10000).setSleepTime(2000)
			.setUserAgent(
					"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");;

	@Override
	public void process(Page page) {
		if (page.getUrl().get().matches(".*/m/[^/]+/?$")) {
			String json = page.getHtml().xpath("//script[@id='jsonLdSchema']/html()").get();
			ReadContext ctx = JsonPath.parse(json, conf);
			page.putField("movieId", page.getHtml().$("meta[name=movieID]", "content").get());
			page.putField("title", ctx.read("$.name"));
			page.putField("director", joinString(ctx, "$.director[*]name"));
			page.putField("genre", joinString(ctx, "$.genre"));
			page.putField("author", joinString(ctx, "$.author[*].name"));
			page.putField("studio", ctx.read("$.productionCompany.name"));
			page.putField("year", ctx.read("$.datePublished"));
			page.putField("mpaaRating", ctx.read("$.contentRating"));
			page.putField("image", ctx.read("$.image"));

			page.putField("link", page.getUrl().toString());
			page.putField("criticConsensus",
					page.getHtml()
							.xpath("//div[@id='all-critics-numbers']//p[@class='critic_consensus superPageFontColor']/allText()")
							.get());
			page.putField("movieSynopsis", page.getHtml().xpath("//div[@id='movieSynopsis']/text()").get());
			page.putField("inTheaters", page.getHtml().xpath("//div[@class='info']")
					.$("div:containsOwn(In Theaters) + div > time", "datetime").get());
			page.putField("onDvd", page.getHtml().xpath("//div[@class='info']")
					.$("div:containsOwn(On DVD) + div > time", "datetime").get());
			page.putField("runTime", page.getHtml().xpath("//div[@class='info']")
					.$("div:containsOwn(Runtime) + div > time", "datetime").get());
			page.putField("timestamp", System.currentTimeMillis());
			processCast(page, ctx);
			processRating(page, ctx);
		} else {
			page.setSkip(true);
		}
		if (page.getStatusCode() == HttpStatus.OK.value()) {
			List<String> links = page.getHtml().links().all();
			for (String link : links) {
				if (!StringUtils.contains(link, "www.rottentomatoes.com/")) {
					continue;
				}
				if (StringUtils.contains(link, "www.rottentomatoes.com/tv/")) {
					continue;
				}
				int index = StringUtils.indexOf(link, "#");
				if (index != -1) {
					link = StringUtils.substring(link, 0, index);
				}
				link = StringUtils.removeEnd(link, "/");
				page.addTargetRequest(link);
			}
			// page.addTargetRequests(page.getHtml().links().regex(".*www\\.rottentomatoes\\.com/.+").all());
		}
	}

	private String joinString(ReadContext ctx, String path) {
		List<String> names = ctx.read(path);
		return StringUtils.join(names, ",");
	}

	private void processRating(Page page, ReadContext ctx) {
		RTRating res = new RTRating();
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		RTCriticScore cs = new RTCriticScore();
		cs.setMeterValue(ctx.read("$.aggregateRating.ratingValue"));
		cs.setReviewsCounted(ctx.read("$.aggregateRating.reviewCount"));

		Selectable scoreStats = page.getHtml().xpath("//div[@id='scoreStats']");
		String criticFresh = scoreStats.$("span:containsOwn(Fresh:) + span", "text").get();
		if (StringUtils.isNotBlank(criticFresh)) {
			try {
				cs.setFresh(nf.parse(criticFresh).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
		String criticRotten = scoreStats.$("span:containsOwn(Rotten:) + span", "text").get();
		if (StringUtils.isNotBlank(criticRotten)) {
			try {
				cs.setRotten(nf.parse(criticRotten).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}

		String criticAvg = scoreStats.$("div > div:contains(Average Rating:)", "text").get();
		criticAvg = StringUtils.removeStart(criticAvg, "Average Rating:");
		cs.setAverageRating(StringUtils.trimToNull(criticAvg));
		res.setCriticScore(cs);

		RTAudienceScore as = new RTAudienceScore();

		String audienceRate = page.getHtml()
				.$("div#scorePanel a[href*=audience_reviews] div[class*=meter-value] > span", "text").get();
		if (StringUtils.isNotBlank(audienceRate)) {
			as.setMeterValue(Integer.parseInt(StringUtils.removeEnd(StringUtils.trimToEmpty(audienceRate), "%")));
		}

		String audienceAvg = page.getHtml()
				.$("div[class*=audiencepanel] div[class*=audience-info] > div:contains(Average Rating:)", "text").get();
		audienceAvg = StringUtils.removeStart(audienceAvg, "Average Rating:");
		as.setAverageRating(StringUtils.trimToEmpty(audienceAvg));

		String audienceUserRating = page.getHtml()
				.$("div[class*=audiencepanel] div[class*=audience-info] > div:contains(User Ratings:)", "text").get();
		if (StringUtils.isNotBlank(audienceUserRating)) {
			audienceUserRating = StringUtils.trimToEmpty(StringUtils.removeStart(audienceUserRating, "User Ratings:"));
			try {
				as.setUserRatings(nf.parse(audienceUserRating).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
		res.setAudienceScore(as);
		page.putField("rating", res);
	}

	private void processCast(Page page, ReadContext ctx) {
		List<RTCast> res = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> actors = ctx.read("$.actors", List.class);
		List<String> characters = ctx.read("$.character");
		HtmlNode castSection = (HtmlNode) page.getHtml().xpath("//div[@class='castSection']");
		for (int i = 0; i < actors.size(); i++) {
			RTCast cast = new RTCast();
			Map<String, Object> actor = actors.get(i);
			cast.setName((String) actor.get("name"));
			cast.setImage((String) actor.get("image"));
			cast.setLink((String) actor.get("sameAs"));

			if (characters.size() == actors.size()) {
				cast.setCharacters(characters.get(i));
			} else {
				cast.setCharacters(castSection.$("a:contains(" + cast.getName() + ") + span", "title").get());
			}
			res.add(cast);
		}
		page.putField("cast", res);
	}

	@Override
	public Site getSite() {
		return site;
	}

	// public static void main(String[] args) {
	// Spider.create(new RottenTomatoesProcessor())
	// .addPipeline(new JsonFilePipeline("/Users/andy/Downloads"))
	// .addUrl("https://www.rottentomatoes.com/")
	// .thread(5)
	// .run();
	// }

}
