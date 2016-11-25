package me.renhai.taurus.service;

import java.io.File;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import me.renhai.taurus.entity.Author;
import me.renhai.taurus.entity.Casting;
import me.renhai.taurus.entity.Celebrity;
import me.renhai.taurus.entity.Director;
import me.renhai.taurus.entity.Movie;
import me.renhai.taurus.entity.Rating;
import me.renhai.taurus.repository.AuthorRepository;
import me.renhai.taurus.repository.CastingRepository;
import me.renhai.taurus.repository.CelebrityRepository;
import me.renhai.taurus.repository.DirectorRepository;
import me.renhai.taurus.repository.MovieRepository;
import me.renhai.taurus.repository.RatingRepository;

//@Service
public class RottenTomatoesService implements CommandLineRunner {
	private static final Logger LOG = LoggerFactory.getLogger(RottenTomatoesService.class);
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private DirectorRepository directorRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private CelebrityRepository celebrityRepository;
	
	@Autowired
	private CastingRepository castingRepository;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		Iterator<File> iter = FileUtils.iterateFiles(new File("/Users/andy/Downloads/rottentomatoes.com.1124"), new String[] {"json"}, false);
		while (iter.hasNext()) {
			File file = iter.next();
			String text = FileUtils.readFileToString(file, "utf-8");
			JSONObject j;
			try {
				j = (JSONObject)JSONObject.parseObject(text);
			} catch (Exception e) {
				LOG.error(e.getMessage() + ": " + text);
				continue;
			}
			Movie movie = new Movie();
			movie.setOuterId(j.getString("movieId"));
			movie.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
			movie.setLink(j.getString("link"));
			movie.setTitle(j.getString("title"));
			movie.setSynopsis(j.getString("movieSynopsis"));
			movie.setMpaaRating(j.getString("mpaaRating"));
			movie.setGenres(j.getString("genre"));
			String runTimeStr = j.getString("runTime");
			if (StringUtils.isNotBlank(runTimeStr)) {
				movie.setRuntime(Integer.parseInt(StringUtils.replacePattern(runTimeStr, "[^\\d]", "")));
			}
			movie.setYear(j.getInteger("year"));
			movie.setInTheatersDate(j.getDate("inTheaters"));
			movie.setOnDvdDate(j.getDate("onDvd"));
			movie.setStudio(j.getString("studio"));
			movie.setImage(j.getString("image"));
			movie.setTimestamp(System.currentTimeMillis());
			Movie m = movieRepository.findByLink(movie.getLink());
			if (m != null) {
				BeanUtils.copyProperties(movie, m, "id");
				movieRepository.save(m);
			} else {
				m = movieRepository.save(movie);
			}
			
			JSONObject r = j.getJSONObject("rating");
			if (r != null) {
				Rating rating = new Rating();
				rating.setMovieId(m.getId());
				rating.setCriticsConsensus(r.getString("criticsConsensus"));
				rating.setCriticRatingValue(r.getInteger("criticRatingValue"));
				rating.setCriticAverageRating(r.getString("criticAverageRating"));
				rating.setCriticReviewsCounted(r.getInteger("criticReviewsCounted"));
				rating.setCriticFresh(r.getInteger("criticFresh"));
				rating.setCriticRotten(r.getInteger("criticRotten"));
				
				rating.setAudienceRatingValue(r.getInteger("audienceRatingValue"));
				rating.setAudienceRatingCount(r.getInteger("audienceRatingCount"));
				rating.setAudienceAverageRating(r.getString("audienceAverageRating"));
				
				Rating ratingOld = ratingRepository.findByMovieId(rating.getMovieId());
				if (ratingOld == null) {
					ratingRepository.save(rating);
				} else {
					BeanUtils.copyProperties(rating, ratingOld, "id");
					ratingRepository.save(ratingOld);
				}
			}
			
			castingRepository.deleteByMovieId(m.getId());
			castingRepository.flush();
			JSONArray casts = j.getJSONArray("cast");
			Iterator<Object> iterator = casts.iterator();
			while (iterator.hasNext()) {
				JSONObject one = (JSONObject)iterator.next();
				String celebrityLink = "https://www.rottentomatoes.com/" + StringUtils.removeStart(one.getString("link"), "/");
				Celebrity ce = celebrityRepository.findByLink(celebrityLink);
				if (ce == null) {
					ce = new Celebrity();
					ce.setName(one.getString("name"));
					ce.setImage(one.getString("image"));
					ce.setLink(celebrityLink);
					ce.setType(one.getString("type"));
					ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
					celebrityRepository.save(ce);
				}
				
				Casting casting = new Casting();
				casting.setMovieId(m.getId());
				casting.setCelebrityId(ce.getId());
				casting.setCharacters(one.getString("characters"));
				castingRepository.save(casting);
			}
			
			directorRepository.deleteByMovieId(m.getId());
			directorRepository.flush();
			JSONArray directors = j.getJSONArray("director");
			iterator = directors.iterator();
			while (iterator.hasNext()) {
				JSONObject one = (JSONObject)iterator.next();
				String celebrityLink = "https://www.rottentomatoes.com/" + StringUtils.removeStart(one.getString("sameAs"), "/");
				Celebrity ce = celebrityRepository.findByLink(celebrityLink);
				if (ce == null) {
					ce = new Celebrity();
					ce.setName(one.getString("name"));
					ce.setImage(one.getString("image"));
					ce.setLink(celebrityLink);
					ce.setType(one.getString("@type"));
					ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
					celebrityRepository.save(ce);
				}
				
				Director dir = new Director();
				dir.setMovieId(m.getId());
				dir.setCelebrityId(ce.getId());
				directorRepository.save(dir);
			}
			
			authorRepository.deleteByMovieId(m.getId());
			authorRepository.flush();
			JSONArray authors = j.getJSONArray("author");
			iterator = authors.iterator();
			while (iterator.hasNext()) {
				JSONObject one = (JSONObject)iterator.next();
				String celebrityLink = "https://www.rottentomatoes.com/" + StringUtils.removeStart(one.getString("sameAs"), "/");
				Celebrity ce = celebrityRepository.findByLink(celebrityLink);
				if (ce == null) {
					ce = new Celebrity();
					ce.setName(one.getString("name"));
					ce.setImage(one.getString("image"));
					ce.setLink(celebrityLink);
					ce.setType(one.getString("@type"));
					ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
					celebrityRepository.save(ce);
				}
				
				Author au = new Author();
				au.setMovieId(m.getId());
				au.setCelebrityId(ce.getId());
				au.setCreateTime(System.currentTimeMillis());
				authorRepository.save(au);
			}
		}
	}
	
	
}
