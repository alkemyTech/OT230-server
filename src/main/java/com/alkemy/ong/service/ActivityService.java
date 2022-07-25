package com.alkemy.ong.service;

import com.alkemy.ong.exception.ActivityNotFoundException;
import com.alkemy.ong.models.request.ActivityRequest;
import com.alkemy.ong.models.response.ActivityResponse;

public interface ActivityService {

    public ActivityResponse create(ActivityRequest request);
    public ActivityResponse update(Long id, ActivityRequest request);
}
