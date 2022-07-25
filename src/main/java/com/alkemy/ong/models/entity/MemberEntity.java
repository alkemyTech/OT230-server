package com.alkemy.ong.models.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Builder
@Getter @Setter
@Table(name = "members")
@NoArgsConstructor @AllArgsConstructor
@SQLDelete(sql = "UPDATE members SET soft_delete = true WHERE members_id = ?")
@Where(clause = "soft_delete = false")
public class MemberEntity {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "members_id", updatable = false)
   private Long id;

   @Column(nullable = false, unique = true, length = 50)
   private String name;

   @Column(name = "facebook_url")
   private String facebookUrl;

   @Column(name = "instagram_url")
   private String instagramUrl;

   @Column(name = "linkedin_url")
   private String linkedinUrl;

   @Column(nullable = false)
   private String image;

   @Column(nullable = false)
   private String description;

   @Column(name = "soft_delete", nullable = false)
   private boolean softDelete;

   @CreationTimestamp
   @Column(name = "time_stamp" ,nullable = false, updatable = false)
   private Timestamp timeStamp;
}
