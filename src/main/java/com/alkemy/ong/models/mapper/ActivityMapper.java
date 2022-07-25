package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.ActivityEntity;
import com.alkemy.ong.models.request.ActivityRequest;
import com.alkemy.ong.models.response.ActivityResponse;
import org.springframework.stereotype.Component;

@Component
public class ActivityMapper {

    public ActivityEntity requestToEntity(ActivityRequest request){
        ActivityEntity entity = new ActivityEntity();
        entity.setName(request.getName());
        entity.setImage(request.getImage());
        entity.setContent(request.getContent());
        return entity;
    }
    
    public ActivityResponse entityToResponse(ActivityEntity entity){
        ActivityResponse response = new ActivityResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        response.setImage(entity.getImage());
        response.setContent(entity.getContent());
        return response;
    }
    
    public ActivityEntity updateEntity(ActivityEntity entity, ActivityRequest request){
        
         if(request.getName()!=null && !request.getName().isEmpty()){
            entity.setName(request.getName());
        }

        if(request.getImage()!=null && !request.getImage().isEmpty()){
            entity.setImage(request.getImage());
        }

        if (request.getContent()!=null && !request.getContent().isEmpty()){
            entity.setContent(request.getContent());
        }
        
        return entity;
    }
}
