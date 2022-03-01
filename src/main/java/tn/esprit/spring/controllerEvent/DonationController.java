package tn.esprit.spring.controllerEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.serviceEvent.DonationService;
import tn.esprit.spring.serviceEvent.EventService;
@RestController
@RequestMapping("/Donation")
public class DonationController {
	@Autowired
	EventService eventService;
	@Autowired
	DonationService donationService;
	
	
	@PutMapping(path="editDonation")
	  public void editDonation(@RequestBody Donation d) {
		donationService.editDonation(d);
	  }
	
	
	  @DeleteMapping("/delete/{idDonation}")
	  @ResponseBody
	  public void deleteDonation(Long IdDonation) {
		  donationService.removeDonatin(IdDonation);
		}
	  
		@PostMapping("/add-donation")
		@ResponseBody
		public void addDonation(@RequestBody Donation donation)
		{
			donationService.addDonation(donation);
		} 
		
		
		@PostMapping("/add-Donation-Event/{idevent}/{IdUser}")
		@ResponseBody
		public Donation  addDonationToUserEvent(@RequestBody Donation donation, @PathVariable("idevent") Long idevent, @PathVariable("IdUser") Long IdUser) {
			return donationService.addandaffectuserEvent(donation, idevent, IdUser);
		}
	
	
}
