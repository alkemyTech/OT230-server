package com.alkemy.ong.service;

import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.request.SlideRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;

import java.io.IOException;
import java.util.List;


public interface SlideService {

    SlideResponse details(Long id) throws SlideNotFoundException;
    
    String delete(Long id) throws SlideNotFoundException;

    List<SlidesBasicResponse> getAllSlides() throws SlideNotFoundException;

    SlideResponse create(SlideRequest slidesRequest) throws IOException;

    SlideResponse update (Long id, SlideRequest slidesRequest) throws IOException;

    List<SlideResponse> getList4Users(Long orgId);
}
