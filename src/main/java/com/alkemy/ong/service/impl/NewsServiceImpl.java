package com.alkemy.ong.service.impl;

import java.util.List;

import com.alkemy.ong.models.entity.CategoryEntity;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.entity.UserEntity;
import com.alkemy.ong.models.response.CategoryPageResponse;
import com.alkemy.ong.models.response.NewsPageResponse;
import com.alkemy.ong.models.response.UsersPaginationResponse;
import com.alkemy.ong.utils.ClassUtil;
import com.alkemy.ong.utils.PaginationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.models.entity.NewsEntity;
import com.alkemy.ong.models.mapper.NewsMapper;
import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.models.response.NewsResponse;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;

import static com.alkemy.ong.utils.ApiConstants.PATH_MEMBERS;

@Service
public class NewsServiceImpl extends ClassUtil<NewsEntity, Long, NewsRepository> implements NewsService {

	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	NewsMapper newsMapper;

	private static final String PATH_NEWS = "/news?page=%d";
	
	@Override
	public NewsResponse getNewsById(Long id) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));	
		NewsResponse newsResponse = newsMapper.toNewsResponse(newsFound);
		return newsResponse;	
	}

	@Override
	public NewsResponse createNews(NewsRequest newsRequest) {
		NewsEntity news = newsMapper.toNewsEntity(newsRequest);
		newsRepository.save(news);
		NewsResponse newsResponse = newsMapper.toNewsResponse(news);
		return newsResponse;
	}

	@Override
	public NewsResponse updateNews(Long id, NewsRequest newsRequest) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));
		NewsEntity updatedNews = newsMapper.updatedNews(newsFound, newsRequest);
		newsRepository.save(updatedNews);
		NewsResponse newsResponse = newsMapper.toNewsResponse(updatedNews);
		return newsResponse;
	}

	@Override
	public String removeNews(Long id) {
		NewsEntity newsFound = newsRepository.findById(id)
				.orElseThrow(() -> new OrgNotFoundException("That news doesn't exist"));
		newsRepository.delete(newsFound);
		return "Successfull removal";
	}

	@Override
	public List<NewsResponse> getNewsList() {
		List<NewsEntity> news = newsRepository.findAll();
		List<NewsResponse> newsResponse = newsMapper.listNewsResponse(news);
		return newsResponse;
	}

	@Override
	public NewsPageResponse getPaginationNews(Integer numberOfPage) {
		if(numberOfPage < 1) {
			throw new OrgNotFoundException("Page not found");
		}

		Page<NewsEntity> page = getPage(numberOfPage);
		String previous = getPrevious(PATH_NEWS, numberOfPage);
		String next = getNext(page, PATH_NEWS, numberOfPage);

		return newsMapper.entityPageToPageResponse(page.getContent(), previous, next);
	}
}
