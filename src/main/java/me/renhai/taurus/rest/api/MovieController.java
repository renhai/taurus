package me.renhai.taurus.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.service.MovieService;
import me.renhai.taurus.vo.CastingItem;
import me.renhai.taurus.vo.FilmographyItem;

@RestController
@RequestMapping("/api")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/celebrity/1.0/{id}")
	public ResponseEntity<List<FilmographyItem>> filmography(@PathVariable("id") Integer celebrityId) throws Exception {
		List<FilmographyItem> res = movieService.getFilmographys(celebrityId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/movie/1.0/{id}")
	public ResponseEntity<List<CastingItem>> casting(@PathVariable("id") Integer movieId) throws Exception {
		List<CastingItem> res = movieService.getCastInfosByMovieId(movieId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
