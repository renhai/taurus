package me.renhai.taurus.repository;

import org.springframework.data.repository.CrudRepository;

import me.renhai.taurus.entity.Rating;

public interface RatingRepository extends CrudRepository<Rating, Integer> {

}
