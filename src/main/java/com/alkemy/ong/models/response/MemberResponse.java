package com.alkemy.ong.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class MemberResponse {
   private String name;
   private String facebook;
   private String instagram;
   private String linkedIn;
   private String description;
   private String image;
}
