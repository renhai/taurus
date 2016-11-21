package me.renhai.taurus.spider.rottentomatoes;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.ReadContext;

import me.renhai.taurus.spider.AbstractSpider;

@Service
public class RottenTomatoesSprider extends AbstractSpider<RTMovie, String> {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesSprider.class);
	private Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL).addOptions(Option.SUPPRESS_EXCEPTIONS);

	@PostConstruct
	private void init() {
		
	}
	@Override
	protected String getUrl(String conditions) {
		/**
		String baseUrl = "https://www.rottentomatoes.com";
		try {
			String url = "https://www.rottentomatoes.com/api/private/v1.0/search/?catCount=2&q=" + URLEncoder.encode(conditions, "UTF-8");
			String jsonStr = IOUtils.toString(new URL(url));
			LOG.info(jsonStr);
			ReadContext context = JsonPath.parse(jsonStr, conf);
			List<String> urls = context.read("$.movies[*].url");
			if (CollectionUtils.isEmpty(urls)) {
				return null;
			}
			return baseUrl + urls.get(0);
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return null;
		*/
		return "https://www.rottentomatoes.com/search/?search=" + conditions;
	}
 
	private By movieId = By.xpath("//meta[@name='movieID']");
	private By summaryResult = By.xpath("//section[@id='SummaryResults']/ul/li/div[@class='details']//a[contains(@href, '/m/')]");
	private By movieInfo = By.id("movieSynopsis");
	private By jsonLdSchema = By.xpath("//script[@id='jsonLdSchema']");
	private By inTheaters = By.xpath("//div[@class='info']/div[contains(text(), 'In Theaters')]/following-sibling::div[1]/time");
	private By onDvd = By.xpath("//div[@class='info']/div[contains(text(), 'On DVD')]/following-sibling::div[1]/time");
	private By runTime = By.xpath("//div[@class='info']/div[contains(text(), 'Runtime')]/following-sibling::div[1]/time");
	private By criticConsensus = By.xpath("//div[@id='all-critics-numbers']//p[@class='critic_consensus superPageFontColor']");
	private By criticFresh = By.xpath("//div[@id='scoreStats']//span[contains(text(), 'Fresh')]/following-sibling::span");
	private By criticRotten = By.xpath("//div[@id='scoreStats']//span[contains(text(), 'Rotten')]/following-sibling::span");
	private By criticAvg = By.xpath("//div[@id='scoreStats']/div[1]");
	private By audienceRate = By.xpath("//a[contains(@href, 'audience_review')]//div[contains(@class,'meter-value')]/span[@class='superPageFontColor']");
	private By audienceAvg = By.xpath("//div[contains(@class, 'audiencepanel')]//span[contains(text(), 'Average Rating')]/..");
	private By audienceUserRating = By.xpath("//div[contains(@class, 'audiencepanel')]//span[contains(text(), 'User Rating')]/..");
	
	protected RTMovie process(String conditions) {

		RTMovie movie = new RTMovie();
		movie.setLink(getDriver().getCurrentUrl());
		
		List<WebElement> results = getDriver().findElements(summaryResult);
		if (CollectionUtils.isEmpty(results)) {
			LOG.warn("NO RESULT RETURN");
			return null;
		}
		
		WebElement target = results.get(0);
		String link = target.getAttribute("href");
		movie.setLink(link);
		target.click();
		
		WebElement movieInfoEle = getDriver().findElement(movieInfo);
		movie.setSynopsis(movieInfoEle.getAttribute("innerText"));
		
		if (isElementPresent(movieId)) {
			movie.setMovieId(Long.parseLong(getDriver().findElement(movieId).getAttribute("content")));
		}
		
		if (isElementPresent(inTheaters)) {
			WebElement inTheatersEle = getDriver().findElement(inTheaters);
			movie.setInTheatersDate(inTheatersEle.getAttribute("datetime"));
		}
		if (isElementPresent(onDvd)) {
			WebElement onDvdEle = getDriver().findElement(onDvd);
			movie.setOnDvdDate(onDvdEle.getAttribute("datetime"));
		}
		if (isElementPresent(runTime)) {
			WebElement runTimeEle = getDriver().findElement(runTime);
			movie.setRuntime(runTimeEle.getAttribute("datetime"));
		}
		if (isElementPresent(criticConsensus)) {
			WebElement criticConsensusEle = getDriver().findElement(criticConsensus);
			movie.setCriticsConsensus(criticConsensusEle.getAttribute("innerText"));
		}

		WebElement script = getDriver().findElement(jsonLdSchema);
		String json = script.getAttribute("innerText");
		LOG.info(json);
		ReadContext ctx = JsonPath.parse(json, conf);
		movie.setTitle(ctx.read("$.name"));
		movie.setDirectedBy(joinString(ctx, "$.director[*]name"));
		movie.setGenres(joinString(ctx, "$.genre"));
		movie.setWrittenBy(joinString(ctx, "$.author[*].name"));
		movie.setStudio(ctx.read("$.productionCompany.name"));
		movie.setYear(ctx.read("$.datePublished"));
		movie.setCast(processCast(ctx));
		movie.setMpaaRating(ctx.read("$.contentRating"));
		movie.setImage(ctx.read("$.image"));
		movie.setRating(processRating(ctx));
		return movie;
	}
	
	private RTRating processRating(ReadContext ctx) {
		RTRating res = new RTRating();
		NumberFormat nf = NumberFormat.getInstance(Locale.US);
		RTCriticScore cs = new RTCriticScore();
		cs.setMeterValue(ctx.read("$.aggregateRating.ratingValue"));
		cs.setReviewsCounted(ctx.read("$.aggregateRating.reviewCount"));
		if (isElementPresent(criticFresh)) {
			try {
				cs.setFresh(nf.parse(getDriver().findElement(criticFresh).getAttribute("innerText")).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
		if (isElementPresent(criticRotten)) {
			try {
				cs.setRotten(nf.parse(getDriver().findElement(criticRotten).getAttribute("innerText")).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
		if (isElementPresent(criticAvg)) {
			String avg = getDriver().findElement(criticAvg).getAttribute("innerText");
			avg = StringUtils.removeStart(avg, "Average Rating:");
			cs.setAverageRating(StringUtils.trimToEmpty(avg));
		}
		res.setCriticScore(cs);
		
		RTAudienceScore as = new RTAudienceScore();
		if (isElementPresent(audienceRate)) {
			String meterValue = getDriver().findElement(audienceRate).getAttribute("innerText");
			as.setMeterValue(Integer.parseInt(StringUtils.removeEnd(StringUtils.trimToEmpty(meterValue), "%")));
		}
		if (isElementPresent(audienceAvg)) {
			String avg = getDriver().findElement(audienceAvg).getAttribute("innerText");
			avg = StringUtils.removeStart(avg, "Average Rating:");
			as.setAverageRating(StringUtils.trimToEmpty(avg));
		}
		if (isElementPresent(audienceUserRating)) {
			String userRating = getDriver().findElement(audienceUserRating).getAttribute("innerText");
			userRating = StringUtils.trimToEmpty(StringUtils.removeStart(userRating, "User Ratings:"));
			try {
				as.setUserRatings(nf.parse(userRating).intValue());
			} catch (ParseException e) {
				LOG.error(e.getMessage());
			}
		}
		res.setAudienceScore(as);
		return res;
	}

	private List<RTCast> processCast(ReadContext ctx) {
		List<RTCast> res = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> actors = ctx.read("$.actors", List.class);
		List<String> characters = ctx.read("$.character");
		for (int i = 0; i < actors.size(); i ++) {
			RTCast cast = new RTCast();
			Map<String, Object> actor = actors.get(i);
			cast.setName((String)actor.get("name"));
			cast.setImage((String)actor.get("image"));
			cast.setLink((String)actor.get("sameAs"));
			if (characters.size() == actors.size()) {
				cast.setCharacters(characters.get(i));
			} else {
				By charXpath = By.xpath("//span[@title=\"" + cast.getName() + "\"]/../following-sibling::span");
				if (isElementPresent(charXpath)) {
					cast.setCharacters(getDriver().findElement(charXpath).getAttribute("title"));
				}
			}
			res.add(cast);
		}
		return res;
	}
	private String joinString(ReadContext ctx, String path) {
		List<String> names = ctx.read(path);
		return StringUtils.join(names, ",");
	}

}
