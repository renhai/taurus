package me.renhai.taurus.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import me.renhai.taurus.vo.CastingItem;
import me.renhai.taurus.vo.FilmographyItem;

@Service
public class MovieService {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<CastingItem> getCastInfosByMovieId(Integer movieId) {
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

	public List<FilmographyItem> getFilmographys(Integer celebrityId) {
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
