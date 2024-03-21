
package com.springboot.main.dto;
import java.time.LocalDate;
import com.springboot.main.model.CarDetails;
import com.springboot.main.model.Customer;
import com.springboot.main.model.Host;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
public class BookingDto {
 
	private int BookingId;

	private double Price;
	private LocalDate FromDate;
	private LocalDate ToDate;
	private String PickupLocation;
	private String DropOfLocation;
	private String BookingStatus;
//	@ManyToOne
//	@JoinColumn(name = "customer_id")
//	private Customer customer;
//	@ManyToOne
//	@JoinColumn(name = "car_id" )
//	private CarDetails carDetails;

 

	public int getBookingId() {
		return BookingId;
	}

	public void setBookingId(int bookingId) {
		BookingId = bookingId;
	}

	public double getPrice() {
		return Price;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public LocalDate getFromDate() {
		return FromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		FromDate = fromDate;
	}

	public LocalDate getToDate() {
		return ToDate;
	}

	public void setToDate(LocalDate toDate) {
		ToDate = toDate;
	}

	public String getPickupLocation() {
		return PickupLocation;
	}

	public void setPickupLocation(String pickupLocation) {
		PickupLocation = pickupLocation;
	}

	public String getDropOfLocation() {
		return DropOfLocation;
	}

	public void setDropOfLocation(String dropOfLocation) {
		DropOfLocation = dropOfLocation;
	}

	public String getBookingStatus() {
		return BookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		BookingStatus = bookingStatus;
	}

	@Override
	public String toString() {
		return "BookingDto [BookingId=" + BookingId + ", Price=" + Price + ", FromDate=" + FromDate + ", ToDate="
				+ ToDate + ", PickupLocation=" + PickupLocation + ", DropOfLocation=" + DropOfLocation
				+ ", BookingStatus=" + BookingStatus + "]";
	}

//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//
//	public CarDetails getCarDetails() {
//		return carDetails;
//	}
//
//	public void setCarDetails(CarDetails carDetails) {
//		this.carDetails = carDetails;
//	}
//
//	@Override
//	public String toString() {
//		return "BookingDto [BookingId=" + BookingId + ", Price=" + Price + ", FromDate=" + FromDate + ", ToDate="
//				+ ToDate + ", PickupLocation=" + PickupLocation + ", DropOfLocation=" + DropOfLocation
//				+ ", BookingStatus=" + BookingStatus + ", customer=" + customer + ", carDetails=" + carDetails + "]";
//	}

	 

}




