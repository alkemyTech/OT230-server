package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.TestimonialEntity;
import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialPageResponse;
import com.alkemy.ong.models.response.TestimonialResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestimonialMapper {

    public TestimonialEntity testimonialEntity(TestimonialRequest testimonial){
        return TestimonialEntity.builder()
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .build();
    }
    public TestimonialResponse testimonialResponse(TestimonialEntity testimonialEntity){
        return TestimonialResponse.builder()
                .id(testimonialEntity.getId())
                .name(testimonialEntity.getName())
                .image(testimonialEntity.getImage())
                .content(testimonialEntity.getContent())
                .timestamp(testimonialEntity.getTimestamp())
                .build();
    }
    public TestimonialEntity updateTestimonial(TestimonialEntity entity, TestimonialRequest request){
        entity.setName(request.getName());
        entity.setImage(request.getImage());
        entity.setContent(request.getContent());
        return entity;
    }

    public List<TestimonialResponse> entities2ListResponse(List<TestimonialEntity> testimonialEntities){
        return testimonialEntities.stream()
                .map(this::testimonialResponse)
                .collect(Collectors.toList());
    }

    public TestimonialPageResponse entityPage2PageResponse(List<TestimonialEntity> testimonials, String previous, String next){
        return TestimonialPageResponse.builder()
                .testimonials(entities2ListResponse(testimonials))
                .next(next)
                .previous(previous)
                .build();
    }
}
