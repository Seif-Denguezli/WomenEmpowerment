package tn.esprit.spring.service.event;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.stripe.exception.StripeException;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Payment;
import tn.esprit.spring.entities.User;

public interface DonationService {
	public Donation addDonation(Donation donation);
	public Donation editDonation(Donation donation,Long idEvent);
	public void removeDonation(Long idDonation);
	public Donation addDonation_to_Event( Long idEvent, Long idUser, Payment pi)  throws StripeException;
	public float totaldonationsByUser(Long id);
	public float maxDonationByUser(Long id);
	
	public Set<Donation> Get_Donation_by_User(Long idUser);
	public Set<Donation> Get_all_Donation();
	public void NeedDonnation(Long idEvent);
	
	
	
    
}
