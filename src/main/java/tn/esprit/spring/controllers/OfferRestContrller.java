package tn.esprit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.repository.IOfferRepository;
import tn.esprit.spring.serviceInterface.offer.IOfferService;

@RestController
public class OfferRestContrller {
	@Autowired
	IOfferService OfferService;
	@Autowired
	IOfferRepository OfferRepo ;
	
	@ApiOperation(value = "Récupérer la liste des Offres")
	@GetMapping("/retrieve-all-Offers")
	@ResponseBody
	public List<Offer> getOffers() {
		return OfferService.getAllOffers();
	}
	
	
	
	@GetMapping("/retrieve-Offer-By-Id/{id}")
	@ResponseBody
	public Offer getOfferById(@PathVariable(value="id") Long offerId) {
		return OfferService.getOfferById(offerId);
	}

	@PostMapping("/add-Offer")
	@ResponseBody
	public  void saveOffer( @RequestBody Offer offer) {
		  OfferService.saveOffer(offer);
	}
	
	@DeleteMapping("/delete-offer/{id}")
	@ResponseBody
	public void deleteOffer (@PathVariable(value="id") Long offerId ) {
		OfferService.deleteOfferById(offerId);
	}
	
	@PutMapping ("/update-Offer")
	public void updateOffer ( @RequestBody Offer offer) {
		 		OfferService.updateOffer(offer);
	}
	
	
	/*@GetMapping("/list/{keyword}")
	@ResponseBody
    public void viewHomePage( @PathVariable(value="keyword") String keyword) {
        List<Offer> listOffers = OfferService.listAll(keyword);
        
    }*/
	
	@GetMapping("/")
    public List <Offer> FilterSearch( @Param("keyword") String keyword) {
        return OfferService.listAll(keyword);
      
    }
     

}
