package com.alkemy.ong.repository;

import com.alkemy.ong.models.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembersRepository extends JpaRepository<MemberEntity, Long> {

   boolean existsByName(String name);

   MemberEntity findByName(String name);
}