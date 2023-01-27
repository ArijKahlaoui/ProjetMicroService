package com.micro.rating.service.services;

import java.util.List;

import com.micro.rating.service.entities.Rating;

public interface RatingService {

	Rating create(Rating rating);
	
	//All ratings
	List<Rating> getAll();
	
	//All by userId
	List<Rating> getRatingByUserId(String userId);
	
	//All by hotel
	List<Rating> getRatingByHotelId(String hotelId);
}
