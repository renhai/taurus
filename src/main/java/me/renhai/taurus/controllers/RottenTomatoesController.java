package me.renhai.taurus.controllers;

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
import me.renhai.taurus.spider.rottentomatoes.RottenTomatoesSprider;
import me.renhai.taurus.spider.rottentomatoes.RottenTomatoesSpriderV2;

@RestController
@RequestMapping("/api/rt")
public class RottenTomatoesController {

	@Autowired
	private RottenTomatoesSpriderV2 rottenTomatoesSpriderV2;
	
	@Autowired
	private RottenTomatoesSprider rottenTomatoesSprider;
	
	@RateLimit(5)
	@GetMapping("/1.0/movies")
	public ResponseEntity<RTMovie> movies(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RTMovie movie = rottenTomatoesSprider.search(query);
		return new ResponseEntity<RTMovie>(movie, HttpStatus.OK);
	}
	
	@RateLimit(10)
	@GetMapping("/2.0/movies")
	public ResponseEntity<RTMovie> moviesV2(
			@RequestParam (value = "q", required = true) String query) throws Exception {
		query = StringUtils.trimToEmpty(query);
		RTMovie movie = rottenTomatoesSpriderV2.search(query);
		return new ResponseEntity<RTMovie>(movie, HttpStatus.OK);
	}
	
	@GetMapping("test")
	public ResponseEntity<String> test() throws Exception {
		Thread.sleep(5000);
		return new ResponseEntity<>("This is an test message.", HttpStatus.OK);
	}
}
