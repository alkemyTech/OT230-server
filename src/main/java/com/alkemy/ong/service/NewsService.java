package com.alkemy.ong.service;

import java.util.List;

import com.alkemy.ong.models.response.NewsPageResponse;
import org.springframework.stereotype.Service;

import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.models.response.NewsResponse;

@Service
public interface NewsService {

	List<NewsResponse> getNewsList();
	NewsResponse getNewsById(Long id);
	NewsResponse createNews(NewsRequest newsRequest);
	NewsResponse updateNews(Long id, NewsRequest newsRequest);
	String removeNews(Long id);
	NewsPageResponse getPaginationNews(Integer numberOfPage);
}
