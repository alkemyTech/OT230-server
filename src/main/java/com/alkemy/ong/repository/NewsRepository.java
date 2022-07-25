package com.alkemy.ong.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.models.entity.NewsEntity;

@Repository
public interface NewsRepository extends CrudRepository<NewsEntity, Long>, JpaRepository<NewsEntity, Long> {
	
	List<NewsEntity> findAll();

}
