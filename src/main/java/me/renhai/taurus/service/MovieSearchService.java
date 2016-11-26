package me.renhai.taurus.service;

import java.util.List;

import javax.persistence.EntityManager;

import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.renhai.taurus.entity.Celebrity;
import me.renhai.taurus.entity.Movie;

@Service
public class MovieSearchService {
	private static final Logger LOG = LoggerFactory.getLogger(MovieSearchService.class);

	private static final int PAGE_SIZE = 10;
	
	@Autowired
    private EntityManager entityManager;
	
	public List<Movie> searchMovieByKeyword(String keyword) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Movie.class).get();
		Query luceneQuery = queryBuilder
				.bool()
				.must(
					queryBuilder
						.keyword()
						.fuzzy()
						.onFields("title", "synopsis")
						.matching(keyword)
						.createQuery()
				)
				.createQuery();
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Movie.class);
		fullTextQuery.setMaxResults(PAGE_SIZE);
		@SuppressWarnings("unchecked")
		List<Movie> entities = fullTextQuery.getResultList();
		
//		List<MovieVo> result = entities.stream().map(entry -> {
//			MovieVo movieVo = new MovieVo();
//			RatingVo ratingVo = new RatingVo();
//			movieVo.setRating(ratingVo);
//			BeanUtils.copyProperties(entry, movieVo);
//			BeanUtils.copyProperties(entry.getRating(), movieVo.getRating());
//			return movieVo;
//		}).collect(Collectors.toList());
//		return result;
		return entities;
	}
	
	public List<Celebrity> searchCelebrity(String keyword) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Celebrity.class).get();
		Query luceneQuery = queryBuilder
				.bool()
				.must(
						queryBuilder
						.keyword()
						.onFields("name")
						.matching(keyword)
						.createQuery()
						)
				.createQuery();
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Celebrity.class);
		fullTextQuery.setMaxResults(PAGE_SIZE);
		@SuppressWarnings("unchecked")
		List<Celebrity> result = fullTextQuery.getResultList();
		return result;
	}
}
