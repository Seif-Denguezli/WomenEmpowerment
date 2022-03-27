package tn.esprit.spring.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Payment;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.entities.WomenNeedDonation;
import tn.esprit.spring.repository.*;
import tn.esprit.spring.serviceInterface.DonationService;
import tn.esprit.spring.serviceInterface.EventService;
@RestController
@RequestMapping("/Donation")
public class DonationController {
	@Autowired
	EventService eventService;
	@Autowired
	DonationService donationService;
	
	
	@Autowired
	DonationRepo donationRepo;
	
	@PostMapping("addWomenNeedDonation")
	@ResponseBody
	public WomenNeedDonation addDonation_to_Event(@RequestBody WomenNeedDonation wommenDonation )   {
		return donationService.addWomanNeedDonation(wommenDonation);
		
	  
	}

	
	
	
	
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
	
	
	
	
	
	
	

	
	@GetMapping("/Get-all-Donation")
	public List<Donation> Get_all_Donation(){
		return donationService.Get_all_Donation();
	}
	




	

	@PutMapping("/Donne-Many-towaman/{idEvent}")
	@ResponseBody
	public void Donne_Many( @PathVariable("idEvent") Long idEvent) {
		 donationService.NeedDonnation(idEvent);
	}
	
	
	
	
	
	  
    @GetMapping("/qrcode/{idDonation}")
	public void takeYourPdfDonation(@PathVariable("idDonation") Long idDonation) throws IOException, InterruptedException {
		
		Donation donation = donationRepo.findById(idDonation).orElse(null);
			//	String text=donation.getCourse().getCourseName()+certificate.getUser().getUsername()+"'mail'"+certificate.getUser().getEmail();
		String text= donation.getEvent().getEventName()+donation.getAmount_forEvent()+donation.getCodePayement()+donation.getDonor().getUsername();
				HttpRequest request = HttpRequest.newBuilder()
						.uri(URI.create("https://codzz-qr-cods.p.rapidapi.com/getQrcode?type=text&value="+text+""))
						.header("x-rapidapi-host", "codzz-qr-cods.p.rapidapi.com")
						.header("x-rapidapi-key", "b648c42070msh2f1e24111397e42p1155f4jsn864d7705eee5")
						.method("GET", HttpRequest.BodyPublishers.noBody())
						.build();
				HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
				System.err.println(response.body());
				
				
				
				donation.setQrcode(response.body().substring(8, 61));
				donationRepo.saveAndFlush(donation);
				
				
		}
	@PostMapping(path="facture/{idDonation}")
	public ResponseEntity<byte[]> factureDonation(@PathVariable("idDonation") Long idDonation) throws IOException, InterruptedException{
		Donation don = donationRepo.findById(idDonation).get();
	HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://yakpdf.p.rapidapi.com/pdf"))
			.header("content-type", "application/json")
			.header("X-RapidAPI-Host", "yakpdf.p.rapidapi.com")
			.header("X-RapidAPI-Key", "b648c42070msh2f1e24111397e42p1155f4jsn864d7705eee5")
			.method("POST", HttpRequest.BodyPublishers.ofString("{\r\n    \"pdf\": {\r\n        \"format\": \"A4\",\r\n        \"printBackground\": true,\r\n        \"scale\": 1\r\n    },\r\n    \"source\": {\r\n        \"html\": \"<!DOCTYPE html><html lang=\\\"en\\\"><head><meta charset=\\\"UTF-8\\\"><meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1.0\\\"></head><body><div><center><h2>invoice</h2></center></br><center><h4>"+don.getDonor().getName()+" Thanks for your help</h4><h5>this event will be transmitted for "+don.getEvent().getEventName()+"</h5></center><h6></h6><h6 >amount</h6><h6> "+don.getAmount_forEvent()+"</h6><img src=\\\""+don.getQrcode()+"\\\"></body></html>\"\r\n    },\r\n    \"wait\": {\r\n        \"for\": \"navigation\",\r\n        \"timeout\": 250,\r\n        \"waitUntil\": \"load\"\r\n    }\r\n}"))
			.build();
	 HttpResponse<byte[]> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofByteArray());
	 byte[] res = response.body();
	 return ResponseEntity.ok()
             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ordeesr.pdf") 
             .contentType(MediaType.APPLICATION_PDF).body(res);
	}
	
	

	}
	
	
	
	
	
	
	
	
	

