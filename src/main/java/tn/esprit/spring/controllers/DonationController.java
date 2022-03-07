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


import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
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
	public Donation addDonation_to_Event(@RequestBody Donation donation, @PathVariable("idEvent") Long idEvent, @PathVariable("idUser") Long idUser) {
		return donationService.addDonation_to_Event(donation, idEvent, idUser);
	}
	/*@PostMapping("/addspecifiqueDonation/{idEvent}/{idUser}/{amount}/{tx}")
	@ResponseBody
	public Donation addspcificdonation(@RequestBody Donation donation, @PathVariable("idEvent") Long idEvent, @PathVariable("idUser") Long idUser, @PathVariable("amount") float amount, @PathVariable("tx") float tx) {
		return donationService.addspecificdonation(donation, idEvent, idUser, amount, tx);
	}*/
	@PostMapping("/add-Donation-EventandTransaction/{idEvent}/{idUser}{idTransaction}")
	@ResponseBody
	public Donation addDonation_to_Event(@RequestBody Donation donation, @PathVariable("idEvent") Long idEvent, @PathVariable("idUser") Long idUser, @PathVariable("idTransaction") Long idTransaction) {
		return donationService.addDonation_to_EventAndTransaction(donation, idEvent, idUser, idTransaction);
	}
	
	
	
	@DeleteMapping("/Delete-Donation/{idDonation}/{idUser}")
	public ResponseEntity<?> Delete_Donation( @PathVariable("idDonation") Long idDonation, @PathVariable("idUser") Long IdUser) {
		return donationService.Delete_Donation(idDonation, IdUser);
	}
	
	
	
	@GetMapping("/Get-Donation-By-user/{IdUser}")
	public Set<Donation> Get_Donation_by_User( @PathVariable("IdUser") Long IdUser){
		return donationService.Get_Donation_by_User(IdUser);
	}

	
	@GetMapping("/Get-all-Donation")
	public Set<Donation> Get_all_Donation(){
		return donationService.Get_all_Donation();
	}
	
	@PutMapping(path="/editDonation")
	public void editDonation(@RequestBody Donation don) {
		donationService.editDonation(don);

	
}
	
	/*@PostMapping("/add-user")
	@ResponseBody
	public void addEvent(@RequestBody User user) {
		donationService.addUser(user);
	}*/

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
	
	
	
}
