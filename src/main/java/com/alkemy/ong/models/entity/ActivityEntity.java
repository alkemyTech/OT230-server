package com.alkemy.ong.models.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import javax.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE activities SET soft_delete = true WHERE id = ?")
@Table(name = "activities")
@Where(clause = "soft_delete = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    @CreationTimestamp
    private Timestamp timestamp;
    @Column(name = "soft_delete", columnDefinition = "boolean default false")
    @Builder.Default
    private Boolean softDelete = false;
}
