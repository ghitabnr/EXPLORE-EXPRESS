package com.example.demo.repository;


import com.example.demo.dao.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ReservRepo extends JpaRepository<Reservation, Long> {

}
