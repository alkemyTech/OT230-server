package com.alkemy.ong.repository;

import com.alkemy.ong.models.entity.ContactEntity;
import com.alkemy.ong.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactsRepository extends JpaRepository<ContactEntity, Long> {

   boolean existsByEmail(String email);
   ContactEntity findByEmail(String email);
   @Query("SELECT c FROM ContactEntity c WHERE c.softDelete = false")
   List<ContactEntity> findBySoftDelete();
}