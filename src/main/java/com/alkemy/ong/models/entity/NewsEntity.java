package com.alkemy.ong.models.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE news SET soft_delete = true WHERE news_id=?")
@Where(clause = "soft_delete = false")
public class NewsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "news_id")
	private Long id;

	@NotBlank(message = "The content can't be blank or null")
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;

	@NotBlank(message = "The content can't be blank or null")
	@Column(name = "content", columnDefinition = "TEXT", nullable = false, unique = true)
	private String content;

	@Column(name = "image", nullable = false, unique = true)
	private String image;

	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private CategoryEntity category;
	
	@NotNull(message = "The category id can't be null")
	@Column(name = "category_id", nullable = false)
	private Long categoryId;
	
	@CreationTimestamp
	@Column(name = "last_modification")
	private Timestamp lastModification;

	@Column(name = "soft_delete")
	@Builder.Default
	private Boolean softDelete = false;

}
