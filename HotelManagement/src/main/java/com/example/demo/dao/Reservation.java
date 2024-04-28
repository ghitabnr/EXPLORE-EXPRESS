package com.example.demo.dao;

import java.util.Date;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;
	private String phone;
	private int rooms;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String checkIn;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String checkOut;

	@ManyToOne
	@JoinColumn(name = "hotel_id", referencedColumnName = "id")
	private Hotel hotel;





	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}


	public Reservation() {
		super();
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}


	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
}
