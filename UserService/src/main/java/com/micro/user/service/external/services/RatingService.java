package com.micro.user.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.micro.user.service.entities.Rating;

@Service
@FeignClient(name="RATING-SERVICE")
public interface RatingService {
	
	@GetMapping
	List<Rating> getAll();
	@PostMapping("/ratings")
	ResponseEntity<Rating> createRating(Rating value);
	
	@PutMapping("/ratings/{ratingId}")
	ResponseEntity<Rating> updRating(@PathVariable("ratingId") String ratingId ,Rating rating);
	
	@DeleteMapping("/ratings/{ratingId}")
	public void deleteRating(@PathVariable("ratingId") String ratingId);
}
