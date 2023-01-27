package com.micro.rating.service.services.Impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.rating.service.entities.Rating;
import com.micro.rating.service.repositories.RatingRepository;
import com.micro.rating.service.services.RatingService;

@Service
public class RatingServiceImpl implements RatingService{
	
	@Autowired
	private RatingRepository RatingRepository;

	@Override
	public Rating create(Rating rating) {

		String randomRatingId = UUID.randomUUID().toString();
		rating.setRatingId(randomRatingId);
		return RatingRepository.save(rating);
	}

	@Override
	public List<Rating> getAll() {
		
		return RatingRepository.findAll();
	}

	@Override
	public List<Rating> getRatingByUserId(String userId) {
		
		return RatingRepository.findByUserId(userId);
	}

	@Override
	public List<Rating> getRatingByHotelId(String hotelId) {
		
		return RatingRepository.findByHotelId(hotelId);
	}

}
