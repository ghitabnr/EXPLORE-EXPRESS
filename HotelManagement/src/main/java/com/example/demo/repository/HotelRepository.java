package com.example.demo.repository;

import com.example.demo.dao.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HotelRepository  extends CrudRepository<Hotel, Integer> {
    List<Hotel> findAll();
    List<Hotel> findByNameContaining(String searchTerm);

}
