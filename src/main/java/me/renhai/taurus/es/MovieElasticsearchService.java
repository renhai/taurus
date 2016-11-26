//package me.renhai.taurus.es;
//
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.query.SearchQuery;
//import org.springframework.web.bind.annotation.RequestParam;
//
//public class MovieElasticsearchService {
//	private static final Logger LOG = LoggerFactory.getLogger(MovieElasticsearchService.class);
//
//	@Autowired
//	private MovieDocRepository movieRepository;
//	
//	@Autowired
//	private ElasticsearchTemplate elasticsearchTemplate;
//	
//	
//	public List<MovieDoc> searchMovieUsingElasticsearch(
//			@RequestParam (value = "q", required = true) String q) throws Exception {
//		q = StringUtils.trimToEmpty(q);
////		List<Movie> movies = movieRepository.findByName("Breathe");
//		org.elasticsearch.index.query.QueryBuilder query = QueryBuilders.matchQuery("name", q);
//		SearchQuery searchQuery = new NativeSearchQueryBuilder()
////				.withSort(SortBuilders.fieldSort("year").order(SortOrder.DESC))
//				.withQuery(query)
//				.build();
//		LOG.info(searchQuery.getQuery().toString());
//		List<MovieDoc> movies = elasticsearchTemplate.queryForList(searchQuery, MovieDoc.class);
//		return movies;
//	}
//}
