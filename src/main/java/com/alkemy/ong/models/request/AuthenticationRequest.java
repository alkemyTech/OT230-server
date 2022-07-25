package com.alkemy.ong.models.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationRequest {
	
	@NonNull
	@Email(message = "Incorrect email")
	private String username;
	@NonNull
	@NotEmpty(message = "Empty password")
	private String password;
		
}
