package tn.esprit.spring.controllers;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.entities.Complaint;
import tn.esprit.spring.service.complaintService;

@RestController
public class complaintController {
@Autowired
complaintService compservice;

  @PostMapping("/addReclamation/{userId}")
@ResponseBody
public ResponseEntity<?> addComplaint(Complaint complaint, Long userId){
	return compservice.addComplaint(complaint, userId);
}

@PutMapping("update/{complaintId}")
@ResponseBody
public void updatereclamation(@RequestBody Complaint complaint,@PathVariable Long complaintId){
	compservice.updatereclamation(complaint, complaintId);

}
@DeleteMapping("delet/{idUser}/{complaintId}")
@ResponseBody
public void deletreclamation(@PathVariable Long idUser,@PathVariable Long complaintId){
	compservice.deletreclamation(idUser, complaintId);
	
}
@GetMapping("/affichReclamation")
@ResponseBody
public void showclamation(@RequestBody Complaint complaint){
	compservice.showclamation(complaint);
}
}

