package com.alkemy.ong.context;

import com.alkemy.ong.ContextTests;
import com.alkemy.ong.models.entity.ActivityEntity;
import com.alkemy.ong.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class ActivityContextTest extends ContextTests {

    @Autowired
    private ActivityRepository activityRepository;


    protected ActivityEntity createActivity() throws Exception{
        ActivityEntity activity = ActivityEntity.builder()
                .name("Activity Test")
                .content("Content")
                .image("url_image")
                .build();
        activityRepository.save(activity);
        return activity;
    }

}
