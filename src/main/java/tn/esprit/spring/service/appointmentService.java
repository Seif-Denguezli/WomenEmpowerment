package tn.esprit.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Appointment;
import tn.esprit.spring.repository.appointmentRepo;
import tn.esprit.spring.repository.serviceRepo;

@Service
public class appointmentService {
@Autowired
appointmentRepo apprepo;
@Autowired
serviceRepo serrepo;
public void addRdv(Appointment apt ,Long serviceId){
	tn.esprit.spring.entities.Service serv= serrepo.findById(serviceId).orElse(null);
	//apt.setService(serv);
	if (serv.getStartDate().before(apt.getAppointmentDate())&&serv.getEndDate().after(apt.getAppointmentDate())){
		apprepo.save(apt);
		
	}
	
}
}
