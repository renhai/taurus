package me.renhai.taurus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renhai.taurus.entity.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
	void deleteByMovieId(Integer movieId);
}
