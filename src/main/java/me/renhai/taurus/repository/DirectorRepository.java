package me.renhai.taurus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renhai.taurus.entity.Director;

public interface DirectorRepository extends JpaRepository<Director, Integer> {
	void deleteByMovieId(Integer movieId);
	Director findByMovieIdAndCelebrityId(Integer movieId, Integer celebrityId);

}
