package tn.esprit.spring.serviceEvent.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.DonationRepo;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.UserRepo;
import tn.esprit.spring.serviceEvent.DonationService;

@Service
public class DonationServiceImpl implements DonationService {
	
	@Autowired
	DonationRepo donationRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	EventRepo eventRepo;
		
		
	
		public void editDonation(Donation donation) {
			donationRepo.saveAndFlush(donation);		
		}



		@Override
		public void removeDonatin(Long idDonation) {
			Donation e = donationRepo.findById(idDonation).orElse(null);
			
			donationRepo.delete(e);
		}



		@Override
		public void addDonation(Donation donation) {
			donationRepo.save(donation);
			
		}



		@Override
		public Donation addandaffectuserEvent(Donation donation, Long idEvent, Long idUser) {
			Event e = eventRepo.findById(idEvent).orElse(null);
			User u = userRepo.findById(idUser).orElse(null);
			donation.setDonor(u);
			donation.setEvent(e);
			return donationRepo.save(donation);
		}
		
		
	
		
		

		
		
		
		
		
		
		
		
		
		
		 
		
		
		
		
		
		
	
		
		

}
