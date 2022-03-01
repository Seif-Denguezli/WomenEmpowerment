package tn.esprit.spring.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Complaint;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.complaintRepo;
import tn.esprit.spring.repository.userRepo;


@Slf4j
@Service
public class complaintService {
@Autowired
complaintRepo comprepo ;
@Autowired
userRepo userrepository;

/*public void addreclamation(Complaint complaint, Long userId ){
User user = userrepository.findById(userId).orElse(null);
user.getComplaints().add(complaint);

	comprepo.save(complaint);
}*/
public ResponseEntity<?> addComplaint(Complaint complaint, Long userId) {

	User u = userrepository.findById(userId).orElse(null);

	
		
		u.getComplaints().add(complaint);
		complaint.setUser(u);
		comprepo.save(complaint);
		return ResponseEntity.ok().body(complaint);
	} 

public void updatereclamation(Complaint complaint,Long complaintId){
	comprepo.saveAndFlush(complaint);
}

public void deletreclamation(Long idUser ,Long complaintId){
	User usr = userrepository.findById(idUser).get();
	Complaint comp= comprepo.findById(complaintId).get();
  
	usr.getComplaints().remove(comp);
	comprepo.deleteById(complaintId);
	
	
	
	
}
public void showclamation(Complaint complaint){
	comprepo.findAll();
}


}
