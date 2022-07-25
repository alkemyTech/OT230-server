package com.alkemy.ong.service.impl;


import com.alkemy.ong.exception.SlideNotFoundException;
import com.alkemy.ong.models.entity.SlideEntity;
import com.alkemy.ong.models.mapper.SlideMapper;
import com.alkemy.ong.models.request.SlideRequest;
import com.alkemy.ong.models.response.SlideResponse;
import com.alkemy.ong.models.response.SlidesBasicResponse;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.SlideService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Service
public class SlideServiceImpl implements SlideService {

    private final SlideRepository slideRepository;
    private final SlideMapper slideMapper;

    public SlideServiceImpl(SlideRepository slideRepository, SlideMapper slideMapper) {
        this.slideRepository = slideRepository;
        this.slideMapper = slideMapper;
    }

    @Override
    public SlideResponse details(Long id) throws SlideNotFoundException {
        SlideEntity entity = this.slideRepository.findById(id).orElse(null);

        if (entity == null) {
            throw new SlideNotFoundException("No slide found with that id");
        }

        return this.slideMapper.entityToResponse(entity);
    }

    @Override
    public String delete(Long id) throws SlideNotFoundException {
        if (this.slideRepository.existsById(id)) {
            this.slideRepository.deleteById(id);
            return "The slide was deleted correctly";
        } else {
            throw new SlideNotFoundException("No slide found with that id");
        }
    }

    @Override
    public List<SlidesBasicResponse> getAllSlides() throws SlideNotFoundException {
        List<SlideEntity> entities = slideRepository.findAll();
        if (entities.isEmpty()) {
            throw new SlideNotFoundException("The Slide List is Empty");
        }
        return this.slideMapper.toBasicListResponse(entities);
    }

    private void verification(SlideRequest slideRequest) {
        if (slideRequest.getSort() == null) {
            try {
                Integer lastSort = (this.slideRepository.findAll().get(slideRepository.findAll().size() - 1)
                        .getSort()) + 1;
                slideRequest.setSort(lastSort);
            } catch (IndexOutOfBoundsException e) {
                slideRequest.setSort(1);
            } catch (SlideNotFoundException ex){
                throw new SlideNotFoundException("Slide List is empty");
            }
        }
    }

    @Override
    public SlideResponse create(SlideRequest slideRequest) throws IOException {
        verification(slideRequest);
        SlideEntity slideEntity = slideMapper.toSlideEntityS3(slideRequest);
        this.slideRepository.save(slideEntity);
        return this.slideMapper.entityToResponse(slideEntity);
    }

    private SlideEntity getById(Long id) {
        return slideRepository.findById(id)
                .orElseThrow(() -> new SlideNotFoundException("Slide ID not found"));
    }

    @Transactional
    @Override
    public SlideResponse update(Long id, SlideRequest slideRequest) throws SlideNotFoundException, IOException {
        SlideEntity slideObteined = getById(id);
        verification(slideRequest);
        this.slideMapper.changeValues(slideObteined, slideRequest);
        SlideEntity slideUpdated = this.slideRepository.save(slideObteined);
        return this.slideMapper.entityToResponse(slideUpdated);
    }

    @Override
    public List<SlideResponse> getList4Users(Long organizationId) throws SlideNotFoundException {
        List<SlideEntity> slideList = this.slideRepository.findAll();
        List<SlideEntity> listOrganization = new ArrayList<>();
        if (!slideList.isEmpty()) {
            for (SlideEntity slideEntity : slideList) {
                if (slideEntity.getOrganizationId().equals(organizationId)) {
                    listOrganization.add(slideEntity);
                }
            }
        }
        if (listOrganization.isEmpty()){
            throw new SlideNotFoundException("Slide list is Empty");
        }
        Collections.sort(listOrganization, Comparator.comparing(SlideEntity::getSort));
        return this.slideMapper.entityList2SlideResponseList(listOrganization);
    }
}
