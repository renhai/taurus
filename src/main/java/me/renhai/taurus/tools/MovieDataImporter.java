package me.renhai.taurus.tools;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

@Component
public class MovieDataImporter {
	private static final Logger LOG = LoggerFactory.getLogger(MovieDataImporter.class);
	
//	private SimpleDateFormat dateformat = new SimpleDateFormat("MMMM dd, yyyy");

	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private DirectorRepository directorRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CelebrityRepository celebrityRepository;
	
	@Autowired
	private CastingRepository castingRepository;
	
	public void processAndMergeData(String text) {
		JSONObject j;
		try {
			j = (JSONObject)JSONObject.parseObject(text);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return;
		}
		if (j == null || j.isEmpty()) return;
		processAndMergeData(j);
	}
	
	public void processAndMergeData(JSONObject j) {
    	LOG.info("received one data and handled: " + j.getString("link"));
		if (j.containsKey("movieId")) {
			Movie movieEntity = saveOrUpdateMovie(j);
			saveOrUpdateCastings(j, movieEntity.getId());
			saveOrUpdateDirectors(j, movieEntity.getId());
			saveOrUpdateAuthors(j, movieEntity.getId());		
		} else if (j.containsKey("actorId")) {
			String link = StringUtils.trimToEmpty(j.getString("link"));
			link = StringUtils.removeEnd(link, "/");
			link = StringUtils.replaceOnce(link, "http:", "https:");
			if (StringUtils.isEmpty(link)) return;
			Celebrity cel = celebrityRepository.findByLink(link);
			String actorId = StringUtils.trimToEmpty(j.getString("actorId"));
			if (cel != null) {
				Celebrity dupcel = celebrityRepository.findBySourceAndActorId(Movie.Source.ROTTEN_TOMATOES.getCode(), actorId);
				if (dupcel != null) {//duplicate found
					deleteDupAndMerge(dupcel, cel);
				}
				cel.setUpdateTime(System.currentTimeMillis());
			} else {
				LOG.info("celebrity not exists: " + link);
				cel = new Celebrity();
				cel.setCreateTime(System.currentTimeMillis());
			}
			cel.setActorId(actorId);
			cel.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
			cel.setName(StringUtils.trim(j.getString("name")));
			cel.setLink(link);
			cel.setImage(StringUtils.trimToEmpty(j.getString("image")));
			cel.setBirthday(j.getDate("birthday"));
			cel.setBirthplace(StringUtils.trimToEmpty(j.getString("birthplace")));
			cel.setBio(StringUtils.trim(j.getString("bio")));
			celebrityRepository.save(cel);
		} 
	} 
	
	private void deleteDupAndMerge(Celebrity dupcel, Celebrity cel) {
		celebrityRepository.delete(dupcel);
		celebrityRepository.flush();
		LOG.info("delete duplicate celebrity: " + ToStringBuilder.reflectionToString(dupcel, ToStringStyle.NO_CLASS_NAME_STYLE));
		
		List<Casting> castings = castingRepository.findByCelebrityId(dupcel.getId());
		if (CollectionUtils.isNotEmpty(castings)) {
			for (Casting casting : castings) {
				LOG.info("casting before change: "
						+ ToStringBuilder.reflectionToString(casting, ToStringStyle.JSON_STYLE));
				casting.setCelebrityId(cel.getId());
				casting.setUpdateTime(System.currentTimeMillis());
				LOG.info("casting after change: "
						+ ToStringBuilder.reflectionToString(casting, ToStringStyle.JSON_STYLE));
				Casting existCasting = castingRepository.findByMovieIdAndCelebrityIdAndCharacters(casting.getMovieId(), casting.getCelebrityId(), casting.getCharacters());
				if (existCasting != null) {
					castingRepository.delete(existCasting);
					castingRepository.flush();
					LOG.info("delete duplicate casting: " + ToStringBuilder.reflectionToString(existCasting, ToStringStyle.NO_CLASS_NAME_STYLE));
				} 
				castingRepository.save(casting);
			}
		}
		
		List<Author> authors = authorRepository.findByCelebrityId(dupcel.getId());
		if (CollectionUtils.isNotEmpty(authors)) {
			for (Author author : authors) {
				LOG.info("author before change: "
						+ ToStringBuilder.reflectionToString(author, ToStringStyle.JSON_STYLE));
				author.setCelebrityId(cel.getId());
				author.setUpdateTime(System.currentTimeMillis());
				LOG.info("author after change: "
						+ ToStringBuilder.reflectionToString(author, ToStringStyle.JSON_STYLE));
				Author dupAuthor = authorRepository.findByMovieIdAndCelebrityId(author.getMovieId(), author.getCelebrityId());
				if (dupAuthor != null) {
					authorRepository.delete(dupAuthor);
					authorRepository.flush();
					LOG.info("delete duplicate author: " + ToStringBuilder.reflectionToString(dupAuthor, ToStringStyle.NO_CLASS_NAME_STYLE));
				}
				authorRepository.save(author);
			}
		}
		
		List<Director> directors = directorRepository.findByCelebrityId(dupcel.getId());
		if (CollectionUtils.isNotEmpty(directors)) {
			for (Director director : directors) {
				LOG.info("director before change: "
						+ ToStringBuilder.reflectionToString(director, ToStringStyle.JSON_STYLE));
				director.setCelebrityId(cel.getId());
				director.setUpdateTime(System.currentTimeMillis());
				LOG.info("director after change: "
						+ ToStringBuilder.reflectionToString(director, ToStringStyle.JSON_STYLE));
				Director dupDirector = directorRepository.findByMovieIdAndCelebrityId(director.getMovieId(), director.getCelebrityId());
				if (dupDirector != null) {
					directorRepository.delete(dupDirector);
					directorRepository.flush();
					LOG.info("delete duplicate director: " + ToStringBuilder.reflectionToString(dupDirector, ToStringStyle.NO_CLASS_NAME_STYLE));
				}
				directorRepository.save(director);
			}
		}
	}

	private Movie buildMovieFromJson(JSONObject j) {
		Movie movie = new Movie();
		movie.setOuterId(j.getString("movieId"));
		movie.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
		movie.setLink(StringUtils.removeEnd(j.getString("link"), "/"));
		movie.setTitle(StringUtils.trimToEmpty(j.getString("title")));
		movie.setSynopsis(StringUtils.trim(j.getString("movieSynopsis")));
		movie.setMpaaRating(StringUtils.trim(j.getString("mpaaRating")));
		movie.setGenres(StringUtils.trim(j.getString("genre")));
		String runTimeStr = j.getString("runTime");
		if (StringUtils.isNotBlank(runTimeStr)) {
			movie.setRuntime(Integer.parseInt(StringUtils.replacePattern(runTimeStr, "[^\\d]", "")));
		}
		movie.setYear(j.getInteger("year"));
		movie.setInTheatersDate(j.getDate("inTheaters"));
		movie.setOnDvdDate(j.getDate("onDvd"));
		movie.setStudio(StringUtils.trim(j.getString("studio")));
		movie.setImage(j.getString("image"));
		movie.setTimestamp(j.getLong("timestamp"));
		
		Rating rating = buildRatingFromJson(j);
		movie.setRating(rating);
		rating.setMovie(movie);
		return movie;
	}
	
	private Movie saveOrUpdateMovie(JSONObject j) {
		Movie newMovie = buildMovieFromJson(j);
		Movie movieEntity = movieRepository.findBySourceAndOuterId(Movie.Source.ROTTEN_TOMATOES.getCode(), newMovie.getOuterId());
		if (movieEntity != null) {
			BeanUtils.copyProperties(newMovie, movieEntity, "id", "rating", "createTime");
			BeanUtils.copyProperties(newMovie.getRating(), movieEntity.getRating(), "movie");
			movieEntity.setUpdateTime(System.currentTimeMillis());
			movieRepository.save(movieEntity);
		} else {
			newMovie.setCreateTime(System.currentTimeMillis());
			movieEntity = movieRepository.save(newMovie);
		}
		return movieEntity;
	}
	
	private Rating buildRatingFromJson(JSONObject j) {
		JSONObject r = j.getJSONObject("rating");
		if (r == null) return null;
		Rating rating = new Rating();
		
		rating.setCriticsConsensus(r.getString("criticsConsensus"));
		rating.setCriticRatingValue(r.getInteger("criticRatingValue"));
		rating.setCriticAverageRating(r.getString("criticAverageRating"));
		rating.setCriticReviewsCounted(r.getInteger("criticReviewsCounted"));
		rating.setCriticFresh(r.getInteger("criticFresh"));
		rating.setCriticRotten(r.getInteger("criticRotten"));
		
		rating.setAudienceRatingValue(r.getInteger("audienceRatingValue"));
		rating.setAudienceRatingCount(r.getInteger("audienceRatingCount"));
		rating.setAudienceAverageRating(r.getString("audienceAverageRating"));
		return rating;
	}
	/*
	private Rating saveOrUpdateRating(JSONObject j, Integer movieId) {
		Rating newRating = buildRatingFromJson(j);
		if (newRating == null) return null;
		newRating.setMovieId(movieId);
		Rating ratingEntity = ratingRepository.findByMovieId(movieId);
		if (ratingEntity == null) {
			newRating.setCreateTime(System.currentTimeMillis());
			ratingEntity = ratingRepository.save(newRating);
		} else {
			BeanUtils.copyProperties(newRating, ratingEntity, "id");
			ratingEntity.setUpdateTime(System.currentTimeMillis());
			ratingRepository.save(ratingEntity);
		}
		return ratingEntity;
	}
	*/
	
	private void saveOrUpdateCastings(JSONObject j, Integer movieId) {
//		castingRepository.deleteByMovieId(movieId);
//		castingRepository.flush();
		
		JSONArray casts = j.getJSONArray("cast");
		Iterator<Object> iterator = casts.iterator();
		while (iterator.hasNext()) {
			JSONObject one = (JSONObject)iterator.next();
			String celebrityLink = formatCelebrityLink(one.getString("sameAs"));
			Celebrity ce = celebrityRepository.findByLink(celebrityLink);
			if (ce == null) {
				ce = new Celebrity();
				ce.setName(one.getString("name"));
				ce.setImage(one.getString("image"));
				ce.setLink(celebrityLink);
				ce.setType(one.getString("@type"));
				ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
				ce.setCreateTime(System.currentTimeMillis());
				celebrityRepository.save(ce);
			}
			
			String characters = StringUtils.trim(one.getString("characters"));
			
			Casting castingEntity = castingRepository.findByMovieIdAndCelebrityIdAndCharacters(movieId, ce.getId(), characters);
			if (castingEntity == null) {
				castingEntity = new Casting();
				castingEntity.setMovieId(movieId);
				castingEntity.setCelebrityId(ce.getId());
				castingEntity.setCharacters(characters);
				castingEntity.setCreateTime(System.currentTimeMillis());
				castingRepository.save(castingEntity);
			} 
		}
	}
	
	private void saveOrUpdateDirectors(JSONObject j, Integer movieId) {
//		directorRepository.deleteByMovieId(movieId);
//		directorRepository.flush();
		
		JSONArray directors = j.getJSONArray("director");
		Iterator<Object> iterator = directors.iterator();
		while (iterator.hasNext()) {
			JSONObject one = (JSONObject)iterator.next();
			String celebrityLink = formatCelebrityLink(one.getString("sameAs"));
			Celebrity ce = celebrityRepository.findByLink(celebrityLink);
			if (ce == null) {
				ce = new Celebrity();
				ce.setName(one.getString("name"));
				ce.setImage(one.getString("image"));
				ce.setLink(celebrityLink);
				ce.setType(one.getString("@type"));
				ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
				ce.setCreateTime(System.currentTimeMillis());
				celebrityRepository.save(ce);
			}
			Director directorEntity = directorRepository.findByMovieIdAndCelebrityId(movieId, ce.getId());
			if (directorEntity == null) {
				directorEntity = new Director();
				directorEntity.setMovieId(movieId);
				directorEntity.setCelebrityId(ce.getId());
				directorEntity.setCreateTime(System.currentTimeMillis());
				directorRepository.save(directorEntity);
			}
		}
	}
	
	private void saveOrUpdateAuthors(JSONObject j, Integer movieId) {
//		authorRepository.deleteByMovieId(movieId);
//		authorRepository.flush();
		
		JSONArray authors = j.getJSONArray("author");
		Iterator<Object> iterator = authors.iterator();
		while (iterator.hasNext()) {
			JSONObject one = (JSONObject)iterator.next();
			String celebrityLink = formatCelebrityLink(one.getString("sameAs"));
			Celebrity ce = celebrityRepository.findByLink(celebrityLink);
			if (ce == null) {
				ce = new Celebrity();
				ce.setName(one.getString("name"));
				ce.setImage(one.getString("image"));
				ce.setLink(celebrityLink);
				ce.setType(one.getString("@type"));
				ce.setSource(Movie.Source.ROTTEN_TOMATOES.getCode());
				ce.setCreateTime(System.currentTimeMillis());
				celebrityRepository.save(ce);
			}
			
			Author authorEntity = authorRepository.findByMovieIdAndCelebrityId(movieId, ce.getId());
			if (authorEntity == null) {
				authorEntity = new Author();
				authorEntity.setMovieId(movieId);
				authorEntity.setCelebrityId(ce.getId());
				authorEntity.setCreateTime(System.currentTimeMillis());
				authorRepository.save(authorEntity);
			}
		}
	}
	
	private String formatCelebrityLink(String path) {
		String celebrityLink = "https://www.rottentomatoes.com/" + StringUtils.removeStart(path, "/");
		celebrityLink = StringUtils.removeEnd(celebrityLink, "/");
		return celebrityLink;
	}
	
	
}
