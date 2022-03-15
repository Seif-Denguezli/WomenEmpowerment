package tn.esprit.spring.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entities.Appointment;
import tn.esprit.spring.entities.Complaint;
import tn.esprit.spring.service.IAppointment;
import tn.esprit.spring.service.appointmentService;

@RestController
public class appointementController {
@Autowired
IAppointment appserv;
@PostMapping("/addrdv/{serviceId}/{userId}/{exId}")
@ResponseBody
public void addRdv(@RequestBody Appointment apt ,@PathVariable Long serviceId,@PathVariable  Long userId,@PathVariable  Long exId){
	appserv.addRdv(apt, serviceId, userId,exId);
}
@PutMapping("/modifier/{serviceId}/{appointmentId}")
public void updateRdv(@RequestBody Appointment apt ,@PathVariable Long serviceId,@PathVariable Long appointmentId){
	appserv.updateRdv(apt, serviceId, appointmentId);
}
@GetMapping("/aff")
public List<Appointment> affichRdv(){
	return appserv.affichRdv();
}
@DeleteMapping("deleteRdv/{appointmentId}")
public void deleteAppoitment(@PathVariable Long appointmentId){
	appserv.deleteAppoitment(appointmentId);
}
@PutMapping ("/nbre")
public void NombresCaseSolved() {
	 appserv.NombresCaseSolved();
}


}
