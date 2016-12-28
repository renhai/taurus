package me.renhai.taurus.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import me.renhai.taurus.entity.Celebrity;
import me.renhai.taurus.entity.Movie;
import me.renhai.taurus.repository.CelebrityRepository;
import me.renhai.taurus.repository.MovieRepository;
import me.renhai.taurus.vo.CastingItem;
import me.renhai.taurus.vo.CelebrityVo;
import me.renhai.taurus.vo.FilmographyItem;
import me.renhai.taurus.vo.MovieVo;
import me.renhai.taurus.vo.RatingVo;

@Service
public class MovieService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private CelebrityRepository celebrityRepository;

	public MovieVo getMovieInfo(Integer movieId) {
		Movie entity = movieRepository.findOne(movieId);
		if (entity == null) return null;
		MovieVo movieVo = new MovieVo();
		movieVo.setRating(new RatingVo());
		BeanUtils.copyProperties(entity, movieVo, "rating");
		BeanUtils.copyProperties(entity.getRating(), movieVo.getRating());
		List<CastingItem> castingItems = getCastInfosByMovieId(movieId);
		movieVo.setCastingItems(castingItems);
		return movieVo;
	}
	
	public CelebrityVo getCelebrityInfo(Integer celebrityId) {
		Celebrity entity = celebrityRepository.findOne(celebrityId);
		if (entity == null) return null;
		CelebrityVo celebrityVo = new CelebrityVo();
		BeanUtils.copyProperties(entity, celebrityVo);
		List<FilmographyItem> filmographyItems = getFilmographys(celebrityId);
		celebrityVo.setFilmographyItems(filmographyItems);
		return celebrityVo;
	}
	
	private List<CastingItem> getCastInfosByMovieId(Integer movieId) {
		String sql = "select celebrity.id as celebrity_id, celebrity.name as celebrity_name, celebrity.image as celebrity_image, casting.characters as characters "
				+ "from casting "
				+ "left join celebrity on celebrity.id = casting.celebrity_id "
				+ "where casting.movie_id = ? "
				+ "order by casting.id asc";
		
		return jdbcTemplate.query(sql, new RowMapper<CastingItem>() {

			@Override
			public CastingItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				CastingItem item = new CastingItem();
				item.setCelebrityId(rs.getInt("celebrity_id"));
				item.setCelebrityName(rs.getString("celebrity_name"));
				item.setCelebrityImage(rs.getString("celebrity_image"));
				item.setCharacters(rs.getString("characters"));
				return item;
			}
			
		}, movieId);
		
	}

	private List<FilmographyItem> getFilmographys(Integer celebrityId) {
		String sql = "select movie.id as movie_id, movie.title as movie_title, movie.year as movie_year, celebrity.name as celebrity_name, rating.critic_rating_value "
				+ "from casting "
				+ "left join movie on movie.id = casting.movie_id "
				+ "left join rating on rating.movie_id = casting.movie_id "
				+ "left join celebrity on celebrity.id = casting.celebrity_id "
				+ "where casting.celebrity_id = ? "
				+ "order by movie.year desc";
		return jdbcTemplate.query(sql, new RowMapper<FilmographyItem>() {

			@Override
			public FilmographyItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				FilmographyItem item = new FilmographyItem();
				item.setMovieId(rs.getInt("movie_id"));
				item.setMovieTitle(rs.getString("movie_title"));
				item.setMovieYear(rs.getInt("movie_year"));
				item.setCelebrityName(rs.getString("celebrity_name"));
				item.setCriticRatingValue(rs.getInt("critic_rating_value"));
				return item;
			}
			
		}, celebrityId);
	}

}
