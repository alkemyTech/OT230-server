package com.alkemy.ong.controller;

import javax.validation.Valid;

import com.alkemy.ong.models.response.NewsPageResponse;
import com.alkemy.ong.models.response.NewsResponse;
import com.alkemy.ong.models.response.UserResponse;
import com.alkemy.ong.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.alkemy.ong.models.request.NewsRequest;
import com.alkemy.ong.service.NewsService;

import lombok.NonNull;

import java.util.Optional;

import static com.alkemy.ong.utils.ApiConstants.BOTH;

@RestController
@RequestMapping("/news")
@Api(value = "News Controller", description = "Crud news, list, search by id and search comments for news")
public class NewsController {
	
	@Autowired
	NewsService newsServ;

	@Autowired
	CommentService commentServ;

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
	@GetMapping()
	@ApiOperation(value = "List news",
				notes = "Return a news list")
	@ApiResponse(code = 200, message = "OK")
	ResponseEntity<?> getAllNews(@RequestParam Optional<Integer> page){
		if (page.isPresent()){
			return new ResponseEntity<>(newsServ.getPaginationNews(page.get()), HttpStatus.OK);
		}else{
			return new ResponseEntity<>(newsServ.getNewsList(), HttpStatus.OK);
		}
	}
	
	@PreAuthorize(BOTH)
	@GetMapping(path = "/{id}")
	@ApiOperation(value = "Find By Id", notes = "Return a NewsResponse")
	@ApiResponses(value = {
		@ApiResponse(code = 200, message = "OK"),
		@ApiResponse(code = 404, message = "NEWS NOT FOUND")
	})
	ResponseEntity<?> getNews(@PathVariable("id") @Valid @NonNull Long id){
		return new ResponseEntity<>(newsServ.getNewsById(id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping()
	@ApiOperation(value = "Create news",
			notes = "Return a newsResponse",
			response = NewsResponse.class)
	@ApiResponse(code = 200, message = "CREATED")
	ResponseEntity<?> createNews(@Valid @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.createNews(news), HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(path = "/{id}")
	@ApiOperation(value = "Update news",
			notes = "Return a newsResponse")
	@ApiResponse(code = 200, message = "OK")
	ResponseEntity<?> putNews(@Valid @PathVariable("id") Long id, @RequestBody NewsRequest news){
		return new ResponseEntity<>(newsServ.updateNews(id, news), HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(path = "/{id}")
	@ApiOperation(value = "Delete news",
			notes = "Return a confirmation")
	@ApiResponse(code = 200, message = "OK")
	ResponseEntity<?> deleteNews(@Valid @PathVariable("id") Long id){
		return new ResponseEntity<>(newsServ.removeNews(id), HttpStatus.OK);
	}
	
	@PreAuthorize(BOTH)
	@GetMapping(path = "/{id}/comments")
	@ApiOperation(value = "Find coments by news Id",
			notes = "Return a coment list")
	@ApiResponse(code = 200, message = "OK")
	ResponseEntity<?> getCommentsByNewsId(@Valid @PathVariable("id") Long id){
		return new ResponseEntity<>(commentServ.findCommentsByNews(id), HttpStatus.OK);
	}
	
}

