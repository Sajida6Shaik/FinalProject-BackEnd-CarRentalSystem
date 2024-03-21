package com.springboot.main.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.springboot.main.model.CarDetails;

 

public interface CarDetailsRepository  extends JpaRepository<CarDetails, Integer>{

 
 
//    @Query(value="select * from car_details c,booking b where c.id=b.car_id",nativeQuery = true)
	
//	List<CarDetails> findBy(String SourceLocation , String   DestinationLocation, LocalDate FromDate);
//
	 @Query("SELECT c FROM CarDetails c WHERE c.sourceLocation = :sourceLocation AND c.destinationLocation = :destinationLocation AND c.fromDate = :fromDate AND c.toDate = :toDate")
	    List<CarDetails> findBySourceLocationAndDestinationLocationAndFromDateAndToDate(
	            @Param("sourceLocation") String sourceLocation,
	            @Param("destinationLocation") String destinationLocation,
	            @Param("fromDate") LocalDate fromDate,
	            @Param("toDate") LocalDate toDate
	    ); 

	 @Query("select c from CarDetails c where c.host.hostId =:hostId")
	 List<CarDetails> findByHostId(int hostId);
}

