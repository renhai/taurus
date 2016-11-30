package me.renhai.taurus.rest.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.crawler.rottentomatoes.RottenTomatoesMovie;
import me.renhai.taurus.crawler.rottentomatoes.RottenTomatoesCrawler;
import me.renhai.taurus.crawler.rottentomatoes.v2.RottenTomatoesCrawlerWebmagic;
import me.renhai.taurus.interceptors.RateLimit;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/rt")
@ApiIgnore
public class RottenTomatoesController {

	@Autowired
	private RottenTomatoesCrawlerWebmagic rottenTomatoesCrawlerWebmagic;
	
	@Autowired
	private RottenTomatoesCrawler rottenTomatoesCrawler;
	
	@RateLimit(5)
	@GetMapping("/1.0/movies")
	public ResponseEntity<RottenTomatoesMovie> movies(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RottenTomatoesMovie movie = rottenTomatoesCrawler.search(query);
		return new ResponseEntity<RottenTomatoesMovie>(movie, HttpStatus.OK);
	}
	
	@RateLimit(30)
	@GetMapping("/2.0/movies")
	public ResponseEntity<RottenTomatoesMovie> moviesV2(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RottenTomatoesMovie movie = rottenTomatoesCrawlerWebmagic.search(query);
		return new ResponseEntity<RottenTomatoesMovie>(movie, HttpStatus.OK);
	}
	
}
