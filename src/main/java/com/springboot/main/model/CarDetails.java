package com.springboot.main.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
@Entity
public class CarDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int carId;
	private String carModel;
	private double price;
	private float mileage;
	private String fuelType;
	private String color;
	private int seating;
	private String insurance;
	private String location;
	private boolean Availability;
	private String sourceLocation;
	private String  destinationLocation;
	private LocalDate fromDate;
	private LocalDate  toDate;
	private String carType;
	
	//Generate getters&setters
	
 @ManyToOne(cascade = CascadeType.ALL)
 
	private Host host;

public int getCarId() {
	return carId;
}

public void setCarId(int carId) {
	this.carId = carId;
}

public String getCarModel() {
	return carModel;
}

public void setCarModel(String carModel) {
	this.carModel = carModel;
}

public double getPrice() {
	return price;
}

public void setPrice(double price) {
	this.price = price;
}

public float getMileage() {
	return mileage;
}

public void setMileage(float mileage) {
	this.mileage = mileage;
}

public String getFuelType() {
	return fuelType;
}

public void setFuelType(String fuelType) {
	this.fuelType = fuelType;
}

public String getColor() {
	return color;
}

public void setColor(String color) {
	this.color = color;
}

public int getSeating() {
	return seating;
}

public void setSeating(int seating) {
	this.seating = seating;
}

public String getInsurance() {
	return insurance;
}

public void setInsurance(String insurance) {
	this.insurance = insurance;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public boolean isAvailability() {
	return Availability;
}

public void setAvailability(boolean availability) {
	Availability = availability;
}

public Host getHost() {
	return host;
}

public void setHost(Host host) {
	this.host = host;
}

public String getSourceLocation() {
	return sourceLocation;
}

public void setSourceLocation(String sourceLocation) {
	this.sourceLocation = sourceLocation;
}

public String getDestinationLocation() {
	return destinationLocation;
}

public void setDestinationLocation(String destinationLocation) {
	this.destinationLocation = destinationLocation;
}

public LocalDate getFromDate() {
	return fromDate;
}

public void setFromDate(LocalDate fromDate) {
	this.fromDate = fromDate;
}

public LocalDate getToDate() {
	return toDate;
}

public void setToDate(LocalDate toDate) {
	this.toDate = toDate;
}

public String getCarType() {
	return carType;
}

public void setCarType(String carType) {
	this.carType = carType;
}

@Override
public String toString() {
	return "CarDetails [carId=" + carId + ", carModel=" + carModel + ", price=" + price + ", mileage=" + mileage
			+ ", fuelType=" + fuelType + ", color=" + color + ", seating=" + seating + ", insurance=" + insurance
			+ ", location=" + location + ", Availability=" + Availability + ", sourceLocation=" + sourceLocation
			+ ", destinationLocation=" + destinationLocation + ", fromDate=" + fromDate + ", toDate=" + toDate
			+ ", carType=" + carType + ", host=" + host + "]";
}

 
 
}