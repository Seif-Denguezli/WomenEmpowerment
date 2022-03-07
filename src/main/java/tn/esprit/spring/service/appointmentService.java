package tn.esprit.spring.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Appointment;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Job;
import tn.esprit.spring.repository.appointmentRepo;
import tn.esprit.spring.repository.serviceRepo;
import tn.esprit.spring.repository.userRepo;

@Service
public class appointmentService implements IAppointment {
@Autowired
appointmentRepo apprepo;
@Autowired
serviceRepo serrepo;
@Autowired
userRepo userrepo;
@Override
public void addRdv(Appointment apt, Long serviceId, Long userId) {
	tn.esprit.spring.entities.Service serv= serrepo.findById(serviceId).orElse(null);
	User user = userrepo.findById(userId).orElse(null);
   // apt.setService(serv);
    apt.setUser(user);
    

	
	if (serv.getStartDate().before(apt.getAppointmentDate())&&serv.getEndDate().after(apt.getAppointmentDate())){
		serv.getAppointments().add(apt);
		
		apprepo.save(apt);
		
	}
}
@Override
public void updateRdv(Appointment apt, Long serviceId, Long appointmentId ) {
	tn.esprit.spring.entities.Service serv= serrepo.findById(serviceId).orElse(null);
	
	Appointment app = apprepo.findById(appointmentId).orElse(null); 
	
	
	
	if (serv.getStartDate().before(apt.getAppointmentDate())&&serv.getEndDate().after(apt.getAppointmentDate())){
		
		app.setAppointmentDate(apt.getAppointmentDate());
		app.setService(apt.getService());
		app.setUser(apt.getUser());
		apprepo.flush();
		
	}
}
@Override
public List<Appointment> affichRdv() {
	return apprepo.findAll();
}
@Override
public void deleteAppoitment(Long appointmentId) {
	apprepo.deleteById(appointmentId);
	
}



}
