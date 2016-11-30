package me.renhai.taurus.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.entity.Celebrity;
import me.renhai.taurus.entity.Movie;
import me.renhai.taurus.service.MovieSearchService;

@RestController
@RequestMapping("/api/search")
public class MovieSearchController {
//	private static final Logger LOG = LoggerFactory.getLogger(MovieSearchController.class);
	
	@Autowired
	private MovieSearchService movieSearchService;
	
	@GetMapping("/1.0/movies")
	public ResponseEntity<List<Movie>> searchMovie(
			@RequestParam (value = "q", required = true) String q) throws Exception {
		q = StringUtils.trimToEmpty(q);
		if (StringUtils.isEmpty(q)) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
		List<Movie> res = movieSearchService.searchMovieByKeyword(q);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/1.0/celebrities")
	public ResponseEntity<List<Celebrity>> searchcelebrity(
			@RequestParam (value = "q", required = true) String q) throws Exception {
		q = StringUtils.trimToEmpty(q);
		if (StringUtils.isEmpty(q)) {
			return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
		}
		List<Celebrity> res = movieSearchService.searchCelebrity(q);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
