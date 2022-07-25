package com.alkemy.ong.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryPageResponse {
    public List<CategoryResponse> categories;
    public String previous;
    public String next;
}
