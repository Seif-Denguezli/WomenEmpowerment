package tn.esprit.spring.service.offer;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Candidacy;
import tn.esprit.spring.entities.Offer;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.CandidacyState;
import tn.esprit.spring.repository.CandidacyRepository;
import tn.esprit.spring.repository.IOfferRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.offer.ICandidacyService;

@Service
@Slf4j
public class CandidacyServiceImpl implements ICandidacyService {

	@Autowired
	CandidacyRepository CandidacyRepo;
	@Autowired
	UserRepository UserRepo;
	@Autowired
	IOfferRepository OfferRepo;
	
	@Override
	public void postulerOffre( Candidacy candidacy,Long offerId, Long userId) {
		

		
		Offer offer = OfferRepo.findById(offerId).orElse(null);
		System.err.println(offer.getOfferId().toString());
		User user= UserRepo.findById(userId).get();
		System.err.println(user.getUserId());
		//log.info(user.getEmail());
		candidacy.setOffer(offer);
		candidacy.setCandidate(user);
		user.getCandidacies().add(candidacy);
		candidacy.setCandidacyState(CandidacyState.Unseen);
		
		// TODO Auto-generated method stub
		CandidacyRepo.save(candidacy);
	   
	}
	
	

}
