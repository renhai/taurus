package me.renhai.taurus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renhai.taurus.entity.Casting;

public interface CastingRepository extends JpaRepository<Casting, Integer> {
	List<Casting> findByMovieId(Integer movieId);
	void deleteByMovieId(Integer movieId);
	Casting findByMovieIdAndCelebrityIdAndCharacters(Integer movieId, Integer celebrityId, String characters);
	List<Casting> findByCelebrityId(Integer celebrityId);

}
