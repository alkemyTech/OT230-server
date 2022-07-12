package com.alkemy.ong.controller;

import com.alkemy.ong.auth.config.seeder.DataBaseSeeder;
import com.alkemy.ong.models.request.UserUpdateRequest;
import com.alkemy.ong.models.response.UserDetailsResponse;
import com.alkemy.ong.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

import static com.alkemy.ong.utils.ApiConstants.ROLE_ADMIN;

@RestController
@RequestMapping(path = "/users")
@PreAuthorize(ROLE_ADMIN)
public class UserController {

   @Autowired
   private UserService userService;



   
   @GetMapping
   public ResponseEntity<?> getAllUsers(@RequestParam Optional<Integer> page ){
	   if(page.isPresent()) {
		   return new ResponseEntity<>(userService.getPaginationUsers(page.get()), HttpStatus.OK);
	   }
	return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
   }

   @PatchMapping(path = "/{id}")
   public ResponseEntity<Void> updateUser(@PathVariable("id") @Valid @NotNull Long id,
                                    @RequestBody @Valid UserUpdateRequest request) {
      userService.updateUser(id, request);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   @DeleteMapping(path = "/{id}")
   public ResponseEntity<Void> deleteUser(@PathVariable("id") @Valid @NotNull Long id) {
      userService.deleteUser(id);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

   @GetMapping(path = "/users")
   public ResponseEntity<List<UserDetailsResponse>> getUsers() {
      return ResponseEntity.ok(userService.getUsers());
   }

}
