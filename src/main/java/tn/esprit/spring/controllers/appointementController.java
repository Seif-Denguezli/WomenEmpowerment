package tn.esprit.spring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.entities.Appointment;
import tn.esprit.spring.service.appointmentService;

@RestController
public class appointementController {
@Autowired
appointmentService appserv;
@PostMapping("/addrdv/{serviceId}")
@ResponseBody
public void addRdv(@RequestBody Appointment apt ,@PathVariable Long serviceId){
	appserv.addRdv(apt, serviceId);
}
}
