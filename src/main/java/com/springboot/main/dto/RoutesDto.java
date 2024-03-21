package com.springboot.main.dto;

import java.time.LocalDate;

import com.springboot.main.model.Booking;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class RoutesDto {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	private String SourceLocation;
	private String DestinationLocation;
	private LocalDate StartDate;
	private LocalDate EndDate;
	
//	@ManyToOne
//    @JoinColumn(name = "booking_id")
//    private Booking booking;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSourceLocation() {
		return SourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		SourceLocation = sourceLocation;
	}

	public String getDestinationLocation() {
		return DestinationLocation;
	}

	public void setDestinationLocation(String destinationLocation) {
		DestinationLocation = destinationLocation;
	}

	public LocalDate getStartDate() {
		return StartDate;
	}

	public void setStartDate(LocalDate startDate) {
		StartDate = startDate;
	}

	public LocalDate getEndDate() {
		return EndDate;
	}

	public void setEndDate(LocalDate endDate) {
		EndDate = endDate;
	}

	@Override
	public String toString() {
		return "RoutesDto [id=" + id + ", SourceLocation=" + SourceLocation + ", DestinationLocation="
				+ DestinationLocation + ", StartDate=" + StartDate + ", EndDate=" + EndDate + "]";
	}

//	public Booking getBooking() {
//		return booking;
//	}
//
//	public void setBooking(Booking booking) {
//		this.booking = booking;
//	}
//
//	@Override
//	public String toString() {
//		return "Routes [id=" + id + ", SourceLocation=" + SourceLocation + ", DestinationLocation="
//				+ DestinationLocation + ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", booking=" + booking
//				+ "]";
//	}
//    
	
	

}
