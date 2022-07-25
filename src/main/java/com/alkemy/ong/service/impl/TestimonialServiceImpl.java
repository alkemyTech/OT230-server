package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.OrgNotFoundException;
import com.alkemy.ong.exception.TestimonialsNotFound;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.entity.TestimonialEntity;
import com.alkemy.ong.models.mapper.TestimonialMapper;
import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialPageResponse;
import com.alkemy.ong.models.response.TestimonialResponse;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.utils.ClassUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import static com.alkemy.ong.utils.ApiConstants.PATH_TESTIMONIALS;

@Service
public class TestimonialServiceImpl extends ClassUtil<TestimonialEntity, Long, TestimonialRepository> implements TestimonialService {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @Autowired
    private TestimonialMapper testimonialMapper;

    @Override
    public TestimonialResponse createTestimonial(TestimonialRequest testimonialRequest) {

        TestimonialEntity testimonialEntity = testimonialMapper.testimonialEntity(testimonialRequest);

        TestimonialResponse response = testimonialMapper.
                testimonialResponse(testimonialRepository.save(testimonialEntity));
        return response;
    }

    @Override
    public TestimonialResponse updateTestimonial(Long id, TestimonialRequest testimonialRequest) {

        TestimonialEntity testimonialEntity = testimonialRepository.findById(id)
                        .orElseThrow(() -> new TestimonialsNotFound("Testimonial not found"));

        TestimonialEntity updateEntity = testimonialMapper.updateTestimonial(
                        testimonialEntity, testimonialRequest);

        TestimonialResponse response = testimonialMapper.testimonialResponse(
                        testimonialRepository.save(updateEntity));

        return response;
    }

    @Override
    public void deleteTestimonial(Long id) {
        TestimonialEntity entity = testimonialRepository.findById(id)
                                    .orElseThrow(() -> new TestimonialsNotFound("Testimonial not found"));
        testimonialRepository.delete(entity);
    }

    @Override
    public TestimonialPageResponse pagination(Integer numberOfPage) {
        if (numberOfPage < 1) {
            throw new TestimonialsNotFound("Resource not found");
        }

        Page<TestimonialEntity> page = getPage(numberOfPage);
        String previous = getPrevious(PATH_TESTIMONIALS, numberOfPage);
        String next = getNext(page, PATH_TESTIMONIALS, numberOfPage);

        return testimonialMapper.entityPage2PageResponse(page.getContent(), previous, next);
    }
}
