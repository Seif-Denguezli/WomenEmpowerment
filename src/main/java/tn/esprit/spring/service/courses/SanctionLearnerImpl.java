package tn.esprit.spring.service.courses;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.SanctionLearnner;
import tn.esprit.spring.enumerations.Penality;
import tn.esprit.spring.repository.CertificateRepository;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.SanctionLearnerRepository;
import tn.esprit.spring.serviceInterface.courses.SanctionLearner;
@Service
public class SanctionLearnerImpl implements SanctionLearner {
@Autowired
CourseRepository courseRepository;
@Autowired
CertificateRepository certificateRepository;
@Autowired
SanctionLearnerRepository sanctionLearnerRepository;
	@Override
	public void Sanction(Long courseId, Long userId,Penality p) {
		SanctionLearnner sc = new 	SanctionLearnner();
		Certificate c = certificateRepository.findByCourseAndByUserId(courseId, userId);
		sc.setCertificate(c);
		sc.setPenality(p);
		sanctionLearnerRepository.save(sc);
		
		
	}
	@Override
	public int PunishmendDecision(Long certificateId) {
		int nbSanctions = 0;
		int warnCount=0;
		List<SanctionLearnner> sl = sanctionLearnerRepository.findByCertificateId(certificateId);
		for (SanctionLearnner sanctionLearner : sl) {
			if(sanctionLearner.getPenality().equals(Penality.KICK))
					{
						certificateRepository.delete(certificateRepository.findById(certificateId).get());
					}
			if(sanctionLearner.getPenality().equals(Penality.WARNING))
					{
						warnCount = warnCount + 1 ;
						if(warnCount==3)
							{
								certificateRepository.delete(certificateRepository.findById(certificateId).get());
							}
					}
			if(sanctionLearner.getPenality().equals(Penality.SANCTION))
					{
						nbSanctions = nbSanctions +1 ;
					}
		}
		return nbSanctions;
	}

	

}
