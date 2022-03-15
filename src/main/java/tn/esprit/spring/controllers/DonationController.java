package tn.esprit.spring.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.exception.StripeException;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Payment;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.service.event.DonationService;
import tn.esprit.spring.service.event.EventService;
@RestController
@RequestMapping("/Donation")
public class DonationController {
	@Autowired
	EventService eventService;
	@Autowired
	DonationService donationService;
	
	
	
	
	/*@PostMapping("/add-Donation")
	@ResponseBody
	public void addEvent(@RequestBody Donation donation) {
		donationService.addDonation(donation);
	}*/
	
	
	
	
	
	
	@PostMapping("/add-Donation-Event/{idEvent}/{idUser}")
	@ResponseBody
	public Donation addDonation_to_Event(@RequestBody Payment payment ,@PathVariable("idEvent") Long idEvent, @PathVariable("idUser") Long idUser)  throws StripeException {
		return donationService.addDonation_to_Event(idEvent, idUser,payment);
	}
	
	@PutMapping(path="editDonation/{idEvent}")
	public Donation editDonation(@RequestBody Donation donation,@PathVariable("idEvent")Long idEvent) {
		
return donationService.editDonation(donation,idEvent);
	}

	
	@DeleteMapping("delete/{idDonation}")
	@ResponseBody
	public void deleteDonation(@PathVariable Long idDonation ){
		donationService.removeDonation(idDonation);
	}
	
	
	
	
	
	
	@GetMapping("/Get-Donation-By-user/{IdUser}")
	public Set<Donation> Get_Donation_by_User( @PathVariable("IdUser") Long IdUser){
		return donationService.Get_Donation_by_User(IdUser);
	}

	
	@GetMapping("/Get-all-Donation")
	public Set<Donation> Get_all_Donation(){
		return donationService.Get_all_Donation();
	}
	



	@PostMapping("/calculdonbyuser/{idUser}")
	@ResponseBody
	public float CalculDonationByUser( @PathVariable("idUser") Long idUser) {
		return donationService.totaldonationsByUser(idUser);
	}
	
	
	@PostMapping("/maxDonationByUser/{idUser}")
	@ResponseBody
	public float maxDonationByUser( @PathVariable("idUser") Long idUser) {
		return donationService.maxDonationByUser(idUser);
	}
	
	@PutMapping("/Donne-Many-towaman/{idEvent}")
	@ResponseBody
	public void Donne_Many( @PathVariable("idEvent") Long idEvent) {
		 donationService.NeedDonnation(idEvent);
	}
	
	
}
