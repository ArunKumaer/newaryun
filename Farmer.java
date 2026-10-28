package com.harvester;

import jakarta.persistence.*;

@Entity
public class Farmer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String phone;
	private String village;
	private double latitude;
	private double longitude;

	public Farmer() {
	}

	public Farmer(String name, String phone, String village, double latitude, double longitude) {
		this.name = name;
		this.phone = phone;
		this.village = village;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getPhone() {
		return phone;
	}

	public String getVillage() {
		return village;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	@Override
	public String toString() {
		return "Farmer{id=" + id + ", name=" + name + ", village=" + village + ", phone=" + phone + "}";
	}
}
