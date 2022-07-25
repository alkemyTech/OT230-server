package com.alkemy.ong.service;

import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberPageResponse;
import com.alkemy.ong.models.response.MemberResponse;

import java.util.List;

public interface MemberService {

   List<MemberResponse> getMembers();
   MemberResponse createMember(MemberRequest request);
   void deleteMember(Long id);
   MemberResponse updateMember(Long id, UpdateMemberRequest request);
   MemberPageResponse pagination(Integer page);
}
