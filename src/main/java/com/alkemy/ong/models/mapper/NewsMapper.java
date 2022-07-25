package com.alkemy.ong.models.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.response.MemberPageResponse;
import com.alkemy.ong.models.response.NewsPageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.models.response.NewsResponse;

@Component
public class NewsMapper {

	public NewsEntity toNewsEntity(NewsRequest newsRequest) {
		NewsEntity news = NewsEntity.builder()
				.name(newsRequest.getName())
				.content(newsRequest.getContent())
				.image(newsRequest.getImage())
				.lastModification(new Timestamp(System.currentTimeMillis()))
				.categoryId(newsRequest.getCategoryId())
				.build();
		
		return news;
	}
	
	public NewsResponse toNewsResponse(NewsEntity news) {
		NewsResponse newsResponse = NewsResponse.builder()
				.name(news.getName())
				.content(news.getContent())
				.image(news.getImage())
				.categoryId(news.getCategoryId())
				.lastModification(news.getLastModification())
				.build();
		
		return newsResponse;	
	}
	
	public NewsEntity updatedNews(NewsEntity news, NewsRequest newsRequest) {
		news.setName(newsRequest.getName());
		news.setContent(newsRequest.getContent());
		news.setImage(newsRequest.getImage());
		news.setCategoryId(newsRequest.getCategoryId());
		
		return news;
		
	}
	
	public List<NewsResponse> listNewsResponse(List<NewsEntity> news) {
		List<NewsResponse> newsResponseList = new ArrayList<>();
		news.forEach((n) -> {
			NewsResponse newsResponse = NewsResponse.builder()
					.name(n.getName())
					.content(n.getContent())
					.image(n.getImage())
					.categoryId(n.getCategoryId())
					.lastModification(n.getLastModification())
					.build();	
			newsResponseList.add(newsResponse);
		});
		
		return newsResponseList;
	}

	public NewsPageResponse entityPageToPageResponse(List<NewsEntity> news, String previous, String next) {
		return NewsPageResponse.builder()
				.news(listNewsResponse(news))
				.previous(previous)
				.next(next)
				.build();
	}
}
