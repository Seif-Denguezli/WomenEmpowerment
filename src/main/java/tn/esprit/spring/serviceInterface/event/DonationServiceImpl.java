package tn.esprit.spring.serviceInterface.event;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Transaction;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.DonationRepo;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.TransactionRepo;
import tn.esprit.spring.repository.UserRepo;
import tn.esprit.spring.service.event.DonationService;

@Service
public class DonationServiceImpl implements DonationService {
	
	@Autowired
	DonationRepo donationRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EventRepo eventRepo;
	@Autowired
	TransactionRepo transactionRepo;
	
	
	@Override
	public Donation addDonation(Donation donation) {
		donationRepo.save(donation);
		
		return donation;
	}
	

	@Override
	public Donation addDonation_to_Event(Donation donation, Long idEvent, Long idUser) {
		Event event = eventRepo.findById(idEvent).orElse(null);
		User user = userRepo.findById(idUser).orElse(null);

		donation.setEvent(event);
		donation.setDonor(user);
		
		
		return donationRepo.save(donation);
		
		
	}
	
	


	@Override
	public Set<Donation> Get_Donation_by_User(Long idUser) {
		User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return user.getDonations();
	}


	@Override
	public Set<Donation> Get_all_Donation() {
	return (Set<Donation>) donationRepo.findAll();
	}


	@Override
	public ResponseEntity<?> Delete_Donation(Long idDonation, Long idUser) {
		if (donationRepo.existsById(idDonation)) {
			Donation don = donationRepo.findById(idDonation)
					.orElseThrow(() -> new EntityNotFoundException("Donation Not Found"));
			User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
			if (don.getDonor().equals(user)) {
				donationRepo.delete(don);
				return ResponseEntity.ok().body("Delete success");
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this post");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Like Not Found");
		}
	}




	@Override
	public Donation editDonation(Donation donation) {
		return donationRepo.saveAndFlush(donation);
	}


	@Override
	public Donation addDonation_to_EventAndTransaction(Donation donation, Long idEvent, Long idUser, Long idTransaction) {
		Event event = eventRepo.findById(idEvent).orElse(null);
		User user = userRepo.findById(idUser).orElse(null);
		Transaction transaction = transactionRepo.findById(idTransaction).orElse(null);
		donation.setEvent(event);
		donation.setDonor(user);
		donation.setTransaction(transaction);
		return donationRepo.save(donation);
		
	}


	/*@Override
	public List<Donation> getUserByAmount(Long idUser) {
		return null;
		
		
	}*/


/*	@Override
	public User addUser(User user) {
		
		return userRepo.save(user);
	}*/


	/*@Override
	public Donation addspecificdonation(Donation donation, Long idEvent, Long idUser, float amount, float tx) {
		Event event = eventRepo.findById(idEvent).orElse(null);
		User user = userRepo.findById(idUser).orElse(null);
		
		donation.setEvent(event);
		donation.setDonor(user);
		
		donation.setAmount(amount);
		donation.setProfit_rate(tx);
		donation.setDonationDate(new Date());
		donation.setAmount_forEvent(amount *(1-tx/100));
		donation.setProfit_amount(amount*tx/100);
		return donationRepo.save(donation);
	}*/


	@Override
	public float totaldonationsByUser(Long id) {
		
		return eventRepo.calcultotaldonationsByUser(id);
	}
	@Override
	public float maxDonationByUser(Long id) {
		
		return eventRepo.maxDonationByUser(id);
	}


    


   








		
		
	
	
		
		
	
		
		

		
		
		
		
		
		
		
		
		
		
		 
		
		
		
		
		
		
	
		
		

}
