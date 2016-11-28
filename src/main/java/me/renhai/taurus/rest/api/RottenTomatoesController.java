package me.renhai.taurus.rest.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.interceptors.RateLimit;
import me.renhai.taurus.spider.rottentomatoes.RTMovie;
import me.renhai.taurus.spider.rottentomatoes.RottenTomatoesSpider;
import me.renhai.taurus.spider.rottentomatoes.v2.RottenTomatoesSpiderV2;

@RestController
@RequestMapping("/api/rt")
public class RottenTomatoesController {

	@Autowired
	private RottenTomatoesSpiderV2 rottenTomatoesSpiderV2;
	
	@Autowired
	private RottenTomatoesSpider rottenTomatoesSpider;
	
	@RateLimit(5)
	@GetMapping("/1.0/movies")
	public ResponseEntity<RTMovie> movies(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RTMovie movie = rottenTomatoesSpider.search(query);
		return new ResponseEntity<RTMovie>(movie, HttpStatus.OK);
	}
	
	@RateLimit(10)
	@GetMapping("/2.0/movies")
	public ResponseEntity<RTMovie> moviesV2(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RTMovie movie = rottenTomatoesSpiderV2.search(query);
		return new ResponseEntity<RTMovie>(movie, HttpStatus.OK);
	}
	
}
