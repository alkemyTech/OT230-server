package com.alkemy.ong.service.impl;

import com.alkemy.ong.exception.MemberNotFoundException;
import com.alkemy.ong.models.entity.MemberEntity;
import com.alkemy.ong.models.mapper.MemberMapper;
import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberPageResponse;
import com.alkemy.ong.models.response.MemberResponse;
import com.alkemy.ong.repository.MembersRepository;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl extends PageUtil<MemberEntity, Long, MembersRepository> implements MemberService{

   @Autowired
   private MembersRepository membersRepository;

   @Autowired
   private MemberMapper memberMapper;

   private static final String PATH_MEMBERS = "?page=%d";

   private MemberEntity findById(Long id) {
      return membersRepository.findById(id)
         .orElseThrow(() -> new MemberNotFoundException("Member not found"));
   }

   @Override
   @Transactional(readOnly = true)
   public MemberPageResponse pagination(Integer numberOfPage) {

      Page<MemberEntity> page = getPage(numberOfPage);
      String previous = getPrevious(PATH_MEMBERS, numberOfPage);
      String next = getNext(page, PATH_MEMBERS, numberOfPage);

      return memberMapper.entityPageToPageResponse(page.getContent(), previous, next);
   }

   @Override
   @Transactional(readOnly = true)
   public List<MemberResponse> getMembers() {
      return memberMapper.entitiesToListOfResponses(membersRepository.findAll());
   }

   @Override
   @Transactional
   public MemberResponse createMember(MemberRequest request) {
      checkName(request.getName());

      MemberEntity member = memberMapper.requestToMemberEntity(request);
      membersRepository.save(member);

      return memberMapper.entityToMemberResponse(member);
   }

   private void checkName(String name) {
      if(membersRepository.existsByName(name))
         throw new RuntimeException("There are a member with the same name");
   }

   @Transactional
   public void deleteMember(Long id) {
      MemberEntity entity = findById(id);
      membersRepository.delete(entity);
   }

   @Transactional
   public MemberResponse updateMember(Long id, UpdateMemberRequest request) {
      MemberEntity member = findById(id);

      member.setName(request.getName());
      member.setFacebookUrl(request.getFacebook());
      member.setInstagramUrl(request.getInstagram());
      member.setLinkedinUrl(request.getLinkedIn());
      member.setDescription(request.getDescription());
      member.setImage(request.getImage());
      membersRepository.save(member);

      return memberMapper.entityToMemberResponse(member);
   }
}
