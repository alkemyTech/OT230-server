package com.alkemy.ong.models.response;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponse {
	
	private String name;
	private String content;
	private String image;
	private Long categoryId;
	private Timestamp lastModification;

}
