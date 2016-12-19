package me.renhai.taurus.rest.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
	public ResponseEntity<Page<Movie>> searchMovie(
			@RequestParam (value = "q", required = true) String q,
			@PageableDefault Pageable pageable
			) throws Exception {
		q = StringUtils.trimToEmpty(q);
		if (StringUtils.isEmpty(q)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		Page<Movie> res = movieSearchService.searchMovieByKeyword(q, pageable);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/1.0/celebrities")
	public ResponseEntity<Page<Celebrity>> searchcelebrity(
			@RequestParam (value = "q", required = true) String q,
			@PageableDefault Pageable pageable) throws Exception {
		q = StringUtils.trimToEmpty(q);
		if (StringUtils.isEmpty(q)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		Page<Celebrity> res = movieSearchService.searchCelebrity(q, pageable);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
