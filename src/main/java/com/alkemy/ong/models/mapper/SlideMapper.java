package com.alkemy.ong.models.mapper;

import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.request.SlideRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.service.AwsS3Service;
import com.alkemy.ong.service.impl.AwsS3ServiceImpl;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SlideMapper {

    private final AwsS3Service awsS3Service;

    public SlideMapper(AwsS3ServiceImpl awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    public SlideResponse entityToResponse(SlideEntity entity) {
        SlideResponse response = new SlideResponse();
        response.setId(entity.getId());
        response.setImageUrl(entity.getImageUrl());
        response.setOrganizationId(entity.getOrganizationId());
        response.setSort(entity.getSort());
        response.setText(entity.getText());
        return response;
    }

    private SlidesBasicResponse toBasicResponse(SlideEntity slideEntity) {
        return new SlidesBasicResponse(slideEntity.getImageUrl(), slideEntity.getSort());
    }

    public List<SlidesBasicResponse> toBasicListResponse(List<SlideEntity> entities) {
        return entities.stream()
                .map(this::toBasicResponse)
                .collect(Collectors.toList());
    }

    public SlideEntity toSlideEntityS3(SlideRequest slidesRequest) throws IOException {
        return SlideEntity.builder()
                .imageUrl(awsS3Service.uploadFileFromBase64(slidesRequest.getImageUrl()))
                .text(slidesRequest.getText())
                .sort(slidesRequest.getSort())
                .organizationId(slidesRequest.getOrganizationId())
                .build();
    }



    public void changeValues(SlideEntity slideEntity, SlideRequest slidesRequest) throws IOException {
        slideEntity.setImageUrl(awsS3Service.uploadFileFromBase64(slidesRequest.getImageUrl()));
        slideEntity.setText(slidesRequest.getText());
        slideEntity.setSort(slidesRequest.getSort());
        slideEntity.setOrganizationId(slidesRequest.getOrganizationId());
    }

    public List<SlideResponse> entityList2SlideResponseList(List<SlideEntity> slideEntities){
        List<SlideResponse> slideResponseList = new ArrayList<>();
        for (SlideEntity slideEntity : slideEntities) {
            slideResponseList.add(this.entityToResponse(slideEntity));
        }
        return slideResponseList;
    }
}
