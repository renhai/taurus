package me.renhai.taurus.es;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface MovieDocRepository extends ElasticsearchRepository<MovieDoc, String> {
	List<MovieDoc> findByName(String name);
}
