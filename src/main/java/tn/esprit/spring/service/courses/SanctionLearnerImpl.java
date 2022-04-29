package tn.esprit.spring.service.courses;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.SanctionLearnner;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Penality;
import tn.esprit.spring.repository.CertificateRepository;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.SanctionLearnerRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.courses.SanctionLearner;
@Service
public class SanctionLearnerImpl implements SanctionLearner {
@Autowired
CourseRepository courseRepository;
@Autowired
CertificateRepository certificateRepository;
@Autowired
SanctionLearnerRepository sanctionLearnerRepository;
@Autowired
UserRepository userRepository;
@Autowired
UserCourseServiceImpl userCourseServiceImpl;
	@Override
	public void Sanction(Long courseId, Long userId,Penality p) {
		SanctionLearnner sc = new 	SanctionLearnner();
		Certificate c = certificateRepository.findByCourseAndByUserId(courseId, userId);
		sc.setCertificate(c);
		sc.setPenality(p);
		sanctionLearnerRepository.save(sc);
		
	}
	@Override
	@Transactional
	@Scheduled(cron="*/10 * * * * *")
	public void PunishmendDecision()
		{
			List<Certificate> certificates = certificateRepository.findAll();
			for (Certificate certificate : certificates)
				{
					int warnCount=0;
					List<SanctionLearnner> sl = sanctionLearnerRepository.findByCertificateId(certificate.getCertificateId());
					
					for (SanctionLearnner sanctionLearner : sl)
					{
						if(sanctionLearner.getPenality().equals(Penality.KICK))
							{
							
							 Course cour = certificate.getCourse();
							   User u = certificate.getUser();
								cour.getBuser().add(certificate.getUser());
							    u.getObtainedCertificates().remove(certificate);	
							    System.err.println(u.getObtainedCertificates());
							    userRepository.save(u);
								courseRepository.save(cour);
								List<SanctionLearnner> sanctions =sanctionLearnerRepository.findByCertificateId(certificate.getCertificateId());
								sanctionLearnerRepository.deleteAll(sanctions);
								certificateRepository.delete(certificate);
								
							
							}
						if(sanctionLearner.getPenality().equals(Penality.WARNING))
							{
								
								warnCount = warnCount + 1 ;
								
								System.err.println( certificate.getCertificateId() + ":::::::" +warnCount);
								
							}
					}
					if(warnCount==3 )
					{	
					
						 Course cour = certificate.getCourse();
						   User u = certificate.getUser();
							cour.getBuser().add(certificate.getUser());
						    u.getObtainedCertificates().remove(certificate);	
						    System.err.println(u.getObtainedCertificates());
						    userRepository.save(u);
							courseRepository.save(cour);
							List<SanctionLearnner> sanctions =sanctionLearnerRepository.findByCertificateId(certificate.getCertificateId());
							sanctionLearnerRepository.deleteAll(sanctions);
							certificateRepository.delete(certificate);
					}
					
					
				}
			
		}

	@Override
	public int userSanctionsByCourse(long userId,long courseId) {
		int pen = 0;
		User user = userRepository.findById(userId).get();
		Set<Certificate> certificates = user.getObtainedCertificates();
		for (Certificate certificate : certificates) {
			List<SanctionLearnner> sl = sanctionLearnerRepository.findByCertificateId(certificate.getCertificateId());
			
			for (SanctionLearnner sanction : sl) {
				if(sanction.getCertificate().getCourse().getCourseId()==courseId && sanction.getPenality().equals(Penality.SANCTION)) {
					pen = pen + 1;
					System.out.println(pen);
				}
			}
		}
		return pen;
	}

	}
	
