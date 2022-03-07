package tn.esprit.spring.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Complaint;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.complaintRepo;


@Slf4j
@Service
public class complaintService implements IComplaint {
@Autowired
complaintRepo comprepo ;
@Autowired
UserRepository userrepository;

@Override
public ResponseEntity<?> addComplaint(Complaint complaint, Long userId) {

	User u = userrepository.findById(userId).orElse(null);

	
		
		u.getComplaints().add(complaint);
		complaint.setUser(u);
		comprepo.save(complaint);
		return ResponseEntity.ok().body(complaint);
	} 

@Override
public void updatereclamation(Complaint complaint,Long complaintId){
	Complaint complaint1= comprepo.findById(complaintId).get();
	complaint1.setComplaintTitle(complaint.getComplaintTitle());
	complaint1.setContent(complaint.getContent());
	comprepo.flush();
	
}
@Override
public void deletreclamation(Long idUser ,Long complaintId){
	User usr = userrepository.findById(idUser).get();
	Complaint comp= comprepo.findById(complaintId).get();
  
	usr.getComplaints().remove(comp);
	comprepo.deleteById(complaintId);
	
	
	
	
}
@Override
public List<Complaint> showclamation(){
	return comprepo.findAll();
}


}
