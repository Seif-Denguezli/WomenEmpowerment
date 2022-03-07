package tn.esprit.spring.serviceInterface.offer;

import tn.esprit.spring.entities.Candidacy;

public interface ICandidacyService {
	
	public void postulerOffre (Candidacy candidacy,Long offerId,Long userId );
	

}
