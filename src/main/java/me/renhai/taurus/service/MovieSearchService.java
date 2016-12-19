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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import me.renhai.taurus.entity.Celebrity;
import me.renhai.taurus.entity.Movie;

@Service
public class MovieSearchService {
	private static final Logger LOG = LoggerFactory.getLogger(MovieSearchService.class);

	@Autowired
    private EntityManager entityManager;
	
	public Page<Movie> searchMovieByKeyword(String keyword, Pageable pageable) {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(Movie.class).get();
		Query luceneQuery = queryBuilder
				.bool()
				.must(
					queryBuilder
						.keyword()
						.fuzzy()
						.onFields("title")
						.matching(keyword)
						.createQuery()
				)
				.createQuery();
		FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, Movie.class);
		fullTextQuery.setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getOffset());
		int resultSize = fullTextQuery.getResultSize();
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
		return new PageImpl<>(entities, pageable, resultSize);
	}
	
	public Page<Celebrity> searchCelebrity(String keyword, Pageable pageable) {
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
		fullTextQuery.setMaxResults(pageable.getPageSize()).setFirstResult(pageable.getOffset());
		int resultSize = fullTextQuery.getResultSize();
		
		@SuppressWarnings("unchecked")
		List<Celebrity> result = fullTextQuery.getResultList();
		return new PageImpl<>(result, pageable, resultSize);
	}
}
