package com.harvester;

import jakarta.persistence.*;

@Entity
public class Harvester {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String name;
	private String ownerName;
	private String cropType;
	private int capacity;
	private double latitude;
	private double longitude;
	private boolean available;

	public Harvester() {
	}

	public Harvester(String name, String ownerName, String cropType, int capacity, double latitude, double longitude,
			boolean available) {
		this.name = name;
		this.ownerName = ownerName;
		this.cropType = cropType;
		this.capacity = capacity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getCropType() {
		return cropType;
	}

	public int getCapacity() {
		return capacity;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public boolean isAvailable() {
		return available;
	}

	@Override
	public String toString() {
		return "Harvester{id=" + id + ", name=" + name + ", owner=" + ownerName + ", crop=" + cropType + ", capacity="
				+ capacity + " acres/day}";
	}
}
