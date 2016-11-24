package me.renhai.taurus.repository;

import org.springframework.data.repository.CrudRepository;

import me.renhai.taurus.entity.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {

}
