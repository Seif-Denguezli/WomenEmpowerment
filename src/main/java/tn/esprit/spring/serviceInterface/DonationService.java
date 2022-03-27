package tn.esprit.spring.serviceInterface;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import com.stripe.exception.StripeException;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Payment;
import tn.esprit.spring.entities.WomenNeedDonation;




public interface DonationService {
	public Donation addDonation(Donation donation);
	public Donation editDonation(Donation donation,Long idEvent);
	public void removeDonation(Long idDonation);
	public Donation addDonation_to_Event( Long idEvent, Long idUser, Payment pi)  throws StripeException;
    public List<Donation> Get_all_Donation();
	public WomenNeedDonation addWomanNeedDonation(WomenNeedDonation wommenNeedDonation);
	public void NeedDonnation(Long idEvent);

	
	
	
    
}
