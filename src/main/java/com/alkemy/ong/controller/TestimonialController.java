package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.TestimonialRequest;
import com.alkemy.ong.models.response.TestimonialResponse;
import com.alkemy.ong.service.TestimonialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.alkemy.ong.utils.ApiConstants.ROLE_ADMIN;

@RestController
@RequestMapping(path = "/testimonials")
@PreAuthorize(ROLE_ADMIN)
public class TestimonialController {

    @Autowired
    private TestimonialService testimonialService;

    @PostMapping
    public ResponseEntity<TestimonialResponse> createTestimonial(
                                                        @Valid @RequestBody TestimonialRequest testimonial){
        return new ResponseEntity<>(testimonialService.createTestimonial(testimonial), HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponse> updateTestimonial(
                                                        @PathVariable Long id,
                                                        @Valid @RequestBody TestimonialRequest testimonial){

        return new ResponseEntity<>(testimonialService.updateTestimonial(id, testimonial),HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestimonial(@PathVariable Long id){

        testimonialService.deleteTestimonial(id);

        return new ResponseEntity<>("Testemional deleted", HttpStatus.OK);
    }
}
