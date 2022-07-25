package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.TestimonialEntity;
import com.alkemy.ong.repository.TestimonialRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class TestimonialContextTest extends ContextTests {

    @Autowired
    private TestimonialRepository testimonialRepository;

    protected TestimonialEntity createTestimonial(){
        return testimonialRepository.save(TestimonialEntity
                .builder()
                        .name("Testimonial Test")
                        .image("Url_Image")
                        .content("content test")
                .build());
    }

    protected String createRequest(String name, String image, String content) throws JsonProcessingException {
        return objectMapper.writeValueAsString(TestimonialEntity
                .builder()
                        .name(name)
                        .image(image)
                        .content(content)
                .build());
    }
}
