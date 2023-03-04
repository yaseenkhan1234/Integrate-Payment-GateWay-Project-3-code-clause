package com.p1;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PayThroughUPIRepo extends JpaRepository<PayThroughUPI, Integer>{
	
		List<PayThroughUPI>	findByupiId(String upi);

}
