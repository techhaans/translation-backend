package com.tech.reprositroy;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tech.model.Customer;
import com.tech.model.Label;
@Repository
public interface labelRepository extends JpaRepository<Label, Long> {
	  //@Query("SELECT l FROM Label l WHERE l.customer.id = :customerId")

	  // List<Label> getLabelsByCustomer(Customer cid);
//    @Query("SELECT l FROM Label l JOIN FETCH l.translations")
//
//	  List<Label> getAllLabelsWithLanguages();

}
