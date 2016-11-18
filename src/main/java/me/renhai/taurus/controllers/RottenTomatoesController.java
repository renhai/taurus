package me.renhai.taurus.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.spider.rottentomatoes.RTMovie;
import me.renhai.taurus.spider.rottentomatoes.RottenTomatoesSprider;

@RestController
@RequestMapping("/api/rt")
public class RottenTomatoesController {

	@Autowired
	private RottenTomatoesSprider rottenTomatoesSprider;
	
	@GetMapping("/1.0/movies")
	public ResponseEntity<RTMovie> movies(
			@RequestParam (value = "q", required = true) String query) {
		query = StringUtils.trimToEmpty(query);
		RTMovie movie = rottenTomatoesSprider.search(query);
		return new ResponseEntity<RTMovie>(movie, HttpStatus.OK);
	}
}
