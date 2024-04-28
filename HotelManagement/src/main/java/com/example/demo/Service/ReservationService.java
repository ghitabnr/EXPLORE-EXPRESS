package com.example.demo.Service;

import com.example.demo.dao.Reservation;
import com.example.demo.repository.ReservRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservRepo reservationRepository;

    public void saveReservation(Reservation reservation) {
        // Logique pour sauvegarder la réservation dans la base de données
        reservationRepository.save(reservation);
    }


}