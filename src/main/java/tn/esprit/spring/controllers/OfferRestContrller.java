package tn.esprit.spring.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nylas.RequestFailedException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import tn.esprit.spring.entities.Candidacy;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.repository.IOfferRepository;
import tn.esprit.spring.service.offer.CalendarServiceImpl;
import tn.esprit.spring.serviceInterface.offer.ICandidacyService;
import tn.esprit.spring.serviceInterface.offer.IOfferService;

@RestController
@RequestMapping("/offer")
public class OfferRestContrller {
	@Autowired
	IOfferService OfferService;
	@Autowired
	IOfferRepository OfferRepo ;
	@Autowired
	ICandidacyService CandidacyService;
	@Autowired
	CalendarServiceImpl userAccount;
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
	
	@GetMapping("/FilterOffer")
    public List <Offer> FilterSearch( @Param("keyword") String keyword) {
        return OfferService.listAll(keyword);
      
    }
	
	@GetMapping("/get-ALL-Candidacies")
    public List <Candidacy> getAllCandidacies( ) {
        return CandidacyService.getAllCandidacies();
      
    }
	
	@PostMapping("/Apply-Offer/{userId}/{offerId}")
	@ResponseBody
	void ApplyOffer(@RequestBody Candidacy candidacy,@PathVariable("userId") Long userId,@PathVariable("offerId") Long offerId) {
		CandidacyService.postulerOffre(candidacy, offerId, userId);
	}
	
	@PutMapping ("/Set-Favorite/{id}")
	@ResponseBody
	public void SetFavorite ( @PathVariable(value="id") Long candidacy_id,boolean is_bookmarked) {
		CandidacyService.SetFavorite(candidacy_id, is_bookmarked);
	}
	
	
	@GetMapping("/listMyCandidacy")
    public List <String>  listMyCandidacy( @Param("keyword") String keyword) {
        return CandidacyService.getMyCandidacy(keyword);
	}
	
	@GetMapping("/listMyFavoriteCandidacy")
    public List <String>  listMyFavoriteCandidacy( @Param("keyword") String keyword) {
        return CandidacyService.getMyFavoriteCandidacy(keyword);
	}
	
	

	@PutMapping ("/Hold-Cnadidacy/{id}")
	@ResponseBody
	public void HoldCandidacy ( @PathVariable(value="id") Long candidacy_id) throws MessagingException {
		CandidacyService.HoldCandidacy(candidacy_id);
	}
	
	@DeleteMapping ("/Restrain-Cnadidacy/{id}")
	@ResponseBody
	public void RestrainCandidacy ( @PathVariable(value="id") Long candidacy_id) throws MessagingException {
		CandidacyService.RestrainCandidacy(candidacy_id);
	}
	@PostMapping("/add-eve")
	@ResponseBody

	public  void interview(Long candidacyId, @PathVariable("hour")int hour,@PathVariable("minutes")int minutes,@RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) throws IOException, RequestFailedException {
		String x ="";
		userAccount.postEventExample( candidacyId,hour,minutes,date);

	}
	@PostMapping("/add-even")
	@ResponseBody

	public  String createcal(long offerId) throws IOException, RequestFailedException {
		String x = "";
		return userAccount.createCal(offerId);

	}
}
