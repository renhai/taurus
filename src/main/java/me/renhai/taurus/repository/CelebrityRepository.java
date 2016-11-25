package me.renhai.taurus.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import me.renhai.taurus.entity.Celebrity;

public interface CelebrityRepository extends JpaRepository<Celebrity, Integer> {
	Celebrity findByLink(String link);
	List<Celebrity> findByName(String name);
}
