package com.micro.hotel.service.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.micro.hotel.service.Repositories.HotelRepository;
import com.micro.hotel.service.entities.Hotel;
import com.micro.hotel.service.services.HotelService;
import com.micro.hotel.service.exceptions.ResourceNotFoundException;

@Service
public class HotelServiceImpl implements HotelService{

	@Autowired
	private HotelRepository hotelRepo;
	
	
	@Override
	public Hotel createHotel(Hotel hotel) {
		//to generate unique Id
		String randomHotelId = UUID.randomUUID().toString();
		hotel.setId(randomHotelId);
		return hotelRepo.save(hotel);
	}

	@Override
	public List<Hotel> getAll() {
		
		return hotelRepo.findAll();
	}

	@Override
	public Hotel get(String id) {
		
		return hotelRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found on server !!: "+id));
	}

}
