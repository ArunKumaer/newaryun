package com.harvester;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "farmer_id")
	private Farmer farmer;

	@ManyToOne
	@JoinColumn(name = "harvester_id")
	private Harvester harvester;

	private LocalDate bookingDate;
	private String status;

	public Booking() {
	}

	public Booking(Farmer farmer, Harvester harvester, LocalDate bookingDate, String status) {
		this.farmer = farmer;
		this.harvester = harvester;
		this.bookingDate = bookingDate;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public Farmer getFarmer() {
		return farmer;
	}

	public Harvester getHarvester() {
		return harvester;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String s) {
		this.status = s;
	}

	@Override
	public String toString() {
		return "Booking{id=" + id + ", farmer=" + farmer.getName() + ", harvester=" + harvester.getName() + ", date="
				+ bookingDate + ", status=" + status + "}";
	}
}
