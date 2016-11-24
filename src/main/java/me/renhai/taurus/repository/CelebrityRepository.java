package me.renhai.taurus.repository;

import org.springframework.data.repository.CrudRepository;

import me.renhai.taurus.entity.Celebrity;

public interface CelebrityRepository extends CrudRepository<Celebrity, Integer> {

}
