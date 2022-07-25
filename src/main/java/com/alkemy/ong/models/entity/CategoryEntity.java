package com.alkemy.ong.models.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@SQLDelete(sql= "UPDATE categories SET soft_delete = true WHERE category_id=?")
@Where(clause = "soft_delete = false")
@ApiModel("Category Model")
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NotNull(message = "Name can't be empty or void.")
    @ApiModelProperty(name = "name", notes = "Name of the category" , example = "Category I", required = true)
    private String name;

    @Column
    @ApiModelProperty(name = "description", notes = "Description of the category" , example = "Description I", required = false)
    private String description;

    @Column
    @ApiModelProperty(name = "Image", notes = "Image of the category" , example = "url_imageI", required = false)
    private String image;

    @CreationTimestamp
    @Column
    private Timestamp timestamp;

    @Column(name = "soft_delete")
    @Builder.Default
    private Boolean softDelete = Boolean.FALSE;

}
