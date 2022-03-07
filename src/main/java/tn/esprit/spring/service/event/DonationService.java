package tn.esprit.spring.service.event;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.User;

public interface DonationService {
	public Donation addDonation(Donation donation);
	public Donation addDonation_to_Event(Donation donation, Long idEvent, Long idUser);
	//public Donation addspecificdonation(Donation donation, Long idEvent, Long idUser , float amount  , float tx);
	public Donation addDonation_to_EventAndTransaction(Donation donation, Long idEvent, Long idUser, Long idTransaction);
	//public ResponseEntity<?> Update_Donation(Donation donation, Long idEvent, Long idUser);
	public float totaldonationsByUser(Long id);
	public float maxDonationByUser(Long id);
	public Donation editDonation(Donation donation);
	public ResponseEntity<?> Delete_Donation(Long idDonation, Long idUser);
	public Set<Donation> Get_Donation_by_User(Long idUser);
	public Set<Donation> Get_all_Donation();
	//public User addUser(User user );
	//SQL
	//public List<Donation> getUserByAmount(Long idUser);
	
    
}
