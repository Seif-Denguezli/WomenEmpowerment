package tn.esprit.spring.service.event;

import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;

public interface DonationService {
	public void editDonation(Donation donation) ;
	public void addDonation(Donation donation);
	public void removeDonatin(Long idDonation);
	public Donation addandaffectuserEvent(Donation donation , Long idEvent , Long idUser);
	
	
}
