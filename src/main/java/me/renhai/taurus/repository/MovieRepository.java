package me.renhai.taurus.repository;

import org.springframework.data.repository.CrudRepository;

import me.renhai.taurus.entity.Movie;

public interface MovieRepository extends CrudRepository<Movie, Integer> {
	Movie findByLink(String link);
}
