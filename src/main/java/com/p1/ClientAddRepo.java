package com.p1;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ClientAddRepo extends JpaRepository<ClientAdd, Integer>{
	
	List<ClientAdd> findByEmail(String email);

}
