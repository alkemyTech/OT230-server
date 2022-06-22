package com.alkemy.ong.models.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE activities SET soft_delete = true WHERE id = ?")
@Table(name = "activities")
@Where(clause = "deleted = false")
public class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private String image;
    @Column(name = "timeStamp")
    private Timestamp timestamp;
    @Column(name = "soft_delete", columnDefinition = "boolean default false")
    private Boolean softDelete = false;
}