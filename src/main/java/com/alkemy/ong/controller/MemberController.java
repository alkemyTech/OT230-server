package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberPageResponse;
import com.alkemy.ong.models.response.MemberResponse;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.alkemy.ong.utils.ApiConstants.*;

@RestController
@RequestMapping(path = "/members")
public class MemberController {

   @Autowired
   private MemberService memberService;

   @GetMapping
   @PreAuthorize(ROLE_ADMIN)
   public ResponseEntity<List<MemberResponse>> getMembers() {
      return ResponseEntity.ok(memberService.getMembers());
   }

   @GetMapping(path = "/get-all")
   @PreAuthorize(ROLE_USER)
   public ResponseEntity<MemberPageResponse> getMembers(@RequestParam(defaultValue = "1") Integer page) {
      return ResponseEntity.ok(memberService.pagination(page));
   }

   @PostMapping
   @PreAuthorize(ROLE_USER)
   public ResponseEntity<MemberResponse> createMember(@RequestBody @Valid MemberRequest request){
      return ResponseEntity.status(HttpStatus.OK).body(memberService.createMember(request));
   }

   @PutMapping(path = "/{id}")
   @PreAuthorize(ROLE_USER)
   public ResponseEntity<MemberResponse> updateMember(@PathVariable("id") Long id,
                                            @RequestBody UpdateMemberRequest update) {
      return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(id, update));
   }

   @DeleteMapping(path = "/{id}")
   @PreAuthorize(BOTH)
   public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
      memberService.deleteMember(id);
      return ResponseEntity.status(HttpStatus.OK).build();
   }
}
