package com.alkemy.ong.controller;

import com.alkemy.ong.models.request.MemberRequest;
import com.alkemy.ong.models.request.UpdateMemberRequest;
import com.alkemy.ong.models.response.MemberResponse;
import com.alkemy.ong.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/members")
public class MemberController {

   @Autowired
   private MemberService memberService;

   @GetMapping
   public ResponseEntity<List<MemberResponse>> getMembers() {
      return ResponseEntity.ok(memberService.getMembers());
   }

   @PostMapping
   public ResponseEntity<Void> createMember(@RequestBody @Valid MemberRequest request){
      memberService.createMember(request);
      return ResponseEntity.status(HttpStatus.OK).build();
   }

   @PutMapping(path = "/{id}")
   public ResponseEntity<Void> updateMember(@PathVariable("id") Long id,
                                            @RequestBody UpdateMemberRequest update) {
      memberService.updateMember(id, update);
      return ResponseEntity.status(HttpStatus.OK).build();
   }

   @DeleteMapping(path = "/{id}")
   public ResponseEntity<Void> deleteMember(@PathVariable("id") Long id) {
      memberService.deleteMember(id);
      return ResponseEntity.status(HttpStatus.OK).build();
   }
}
