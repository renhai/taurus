package me.renhai.taurus.rest.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.service.MovieService;
import me.renhai.taurus.vo.CelebrityVo;
import me.renhai.taurus.vo.MovieVo;

@RestController
@RequestMapping("/api")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/celebrity/1.0/{id}")
	public ResponseEntity<CelebrityVo> filmography(@PathVariable("id") Integer celebrityId) throws Exception {
		CelebrityVo res = movieService.getCelebrityInfo(celebrityId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/movie/1.0/{id}")
	public ResponseEntity<MovieVo> casting(@PathVariable("id") Integer movieId) throws Exception {
		MovieVo res = movieService.getMovieInfo(movieId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
