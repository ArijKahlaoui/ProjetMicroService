package com.micro.user.service.services.impl;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.micro.user.service.entities.Hotel;
import com.micro.user.service.entities.Rating;
import com.micro.user.service.entities.User;
import com.micro.user.service.exceptions.ResourceNotFoundException;
import com.micro.user.service.external.services.HotelService;
import com.micro.user.service.repositories.UserRepository;
import com.micro.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;
	
	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public User saveUser(User user) {
		//to generate unique Id
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepo.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
		
		
		return userRepo.findAll();
	}

	@Override
	public User getUser(String userId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id not found on server !!: "+userId));
		
		//fetch rating of the above user from RATTING SERVICE
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(),Rating[].class);
		logger.info("{}",ratingsOfUser);
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		
		List<Rating> ratingList = ratings.stream().map(rating -> {
			//api call to hotel service to get the hotel
			//avec RestTemplate 
			
			/*ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(),Hotel.class);
			Hotel hotel = forEntity.getBody();
			logger.info("response status code: {}",forEntity.getStatusCode());*/
			
			//avec Feign Client
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			
			
			//set hotel to rating 	
			rating.setHotel(hotel);
			
			return rating;
			
		}).collect(Collectors.toList());
		
		user.setRatings(ratingList);
		return user;
	}

}
