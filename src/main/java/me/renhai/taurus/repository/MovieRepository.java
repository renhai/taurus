package me.renhai.taurus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renhai.taurus.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	Movie findByLink(String link);
	Movie findBySourceAndOuterId(Integer source, String outerId);
}
