package me.renhai.taurus.controllers;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.renhai.taurus.es.MovieDoc;
import me.renhai.taurus.es.MovieRepository;

@RestController
@RequestMapping("/api/search")
public class MovieSearchController {
	private static final Logger LOG = LoggerFactory.getLogger(MovieSearchController.class);
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@GetMapping("/1.0/movies")
	public ResponseEntity<List<MovieDoc>> movies(
			@RequestParam (value = "q", required = true) String q) throws Exception {
		q = StringUtils.trimToEmpty(q);
//		List<Movie> movies = movieRepository.findByName("Breathe");
		QueryBuilder query = QueryBuilders.matchQuery("name", q);
		SearchQuery searchQuery = new NativeSearchQueryBuilder()
//				.withSort(SortBuilders.fieldSort("year").order(SortOrder.DESC))
				.withQuery(query)
				.build();
		LOG.info(searchQuery.getQuery().toString());

		List<MovieDoc> movies = elasticsearchTemplate.queryForList(searchQuery, MovieDoc.class);
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
}
