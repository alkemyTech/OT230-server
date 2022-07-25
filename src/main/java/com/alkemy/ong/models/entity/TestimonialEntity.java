package com.alkemy.ong.models.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "testimonials")
@Getter
@Setter
@Builder
@SQLDelete(sql = "UPDATE testimonials SET soft_delete = true WHERE testimonial_id=?")
@Where(clause = "soft_delete = false")
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "testimonial_id")
    private Long id;

    @NotBlank
    @NotNull(message = "the name can't be null")
    @NotEmpty(message = "the name can't be empty")
    private String name;
    
    @NotBlank
    @NotNull(message = "the image can't be null")
    @NotEmpty(message = "the image can't be empty")
    private String image;
    
    private String content;
    
    @Column(name = "timeStamp")
    @CreationTimestamp
    private Timestamp timestamp;
    
    @Column(name = "soft_delete")
    @Builder.Default
    private Boolean softDelete = Boolean.FALSE;
}
