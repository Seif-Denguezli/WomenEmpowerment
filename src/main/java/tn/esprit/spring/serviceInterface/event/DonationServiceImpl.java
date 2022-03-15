package tn.esprit.spring.serviceInterface.event;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Payment;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.WomenNeedDonation;
import tn.esprit.spring.repository.DonationRepo;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.PostRepo;

import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.repository.WomenNeedHelpRepo;
import tn.esprit.spring.service.event.DonationService;

@Service
public class DonationServiceImpl implements DonationService {

	@Autowired
	DonationRepo donationRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	EventRepo eventRepo;

	@Autowired
	PostRepo PR;

	@Autowired
	WomenNeedHelpRepo womanNeedHelpRepo;

	@Autowired
	PaymentService ps;
	@Override
	public Donation addDonation(Donation donation) {
		donationRepo.save(donation);

		return donation;
	}
	
	
	
	

	@Override
	public Donation editDonation(Donation donation, Long idEvent) {
		donation.setAmount_forEvent(donation.getAmount_forEvent());
		
		Event event = eventRepo.findById(idEvent).orElse(null);
		donation.setEvent(event);
		
		donationRepo.flush();
		return donation;
		
	}

	@Override
	public void removeDonation(Long idDonation) {
		Donation donation = donationRepo.findById(idDonation).orElse(null);
		donationRepo.delete(donation);

	}

	@Override
	public Set<Donation> Get_all_Donation() {
		return (Set<Donation>) donationRepo.findAll();
	}

	@Override
	public Donation addDonation_to_Event( Long idEvent, Long idUser,Payment pi)   throws StripeException {
		Donation donation = new Donation();
		
		Event event = eventRepo.findById(idEvent).orElse(null);
		User user = userRepo.findById(idUser).orElse(null);
		PaymentIntent p =ps.paymentIntent(pi);
		donation.setEvent(event);
		donation.setDonor(user);
		donation.setAmount_forEvent(p.getAmount());
		//ps.confirm(p.getId());
		return donationRepo.save(donation);

	}

	@Override
	public Set<Donation> Get_Donation_by_User(Long idUser) {
		User user = userRepo.findById(idUser).orElseThrow(() -> new EntityNotFoundException("User not found"));
		return user.getDonations();
	}

	@Override
	public void NeedDonnation(Long idEvent) {
		Event e = eventRepo.findById(idEvent).orElse(null);
		float montant = 0;
		for (Donation d : donationRepo.findAll()) {
			if (d.getEvent().getEventId() == idEvent) {
				montant = montant + d.getAmount_forEvent();
			}

		}
		if (PR.diffrence_entre_date(e.getEndAt()) <= 0) {
			for (WomenNeedDonation w : womanNeedHelpRepo.findAll()) {
				if (w.getPriority() == 1) {
					w.setGetHelp(true);
					w.setMontantRecu(montant);
				}
				womanNeedHelpRepo.save(w);
			}
			for (WomenNeedDonation w : womanNeedHelpRepo.findAll()) {
				w.setPriority(w.getPriority() - 1);
				womanNeedHelpRepo.save(w);
			}

		}
	}

	@Override
	public float totaldonationsByUser(Long id) {

		return eventRepo.calcultotaldonationsByUser(id);
	}

	@Override
	public float maxDonationByUser(Long id) {

		return eventRepo.maxDonationByUser(id);
	}

}
