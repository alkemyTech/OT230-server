package com.alkemy.ong.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alkemy.ong.models.entity.NewsEntity;

@Repository
public interface NewsRepository extends CrudRepository<NewsEntity, Long> {

}