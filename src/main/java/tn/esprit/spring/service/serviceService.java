package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import tn.esprit.spring.entities.*;
import tn.esprit.spring.repository.serviceRepo;
import tn.esprit.spring.repository.userRepo;


@Service
public class serviceService {
	@Autowired
	serviceRepo servrepo;
	@Autowired
	userRepo userrepo;

	
	public void addService(tn.esprit.spring.entities.Service s , Long userId){
		User u = userrepo.findById(userId).orElse(null);
		u.getCreatedServices().add(s);
	

		servrepo.save(s);
	}
	public void updateService(tn.esprit.spring.entities.Service s ){
		
		
		servrepo.saveAndFlush(s);
	}

}
