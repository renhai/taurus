package me.renhai.taurus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import me.renhai.taurus.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
	Rating findByMovieId(Integer movieId);
}
