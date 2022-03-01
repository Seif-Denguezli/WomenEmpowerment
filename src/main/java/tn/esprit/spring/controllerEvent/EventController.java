package tn.esprit.spring.controllerEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.serviceEvent.EventService;

@RestController
@RequestMapping("/Event")
public class EventController {
	@Autowired
	EventService eventService;
	@PutMapping("/Update-Event/{idEvent}/{IdUser}")
	@ResponseBody
	public ResponseEntity<?> Update_Post(@RequestBody Event event, @PathVariable("idEvent") Long idEvent, @PathVariable("IdUser") Long IdUser) {
		return eventService.Update_Event(event,idEvent,IdUser);
	}
	@PostMapping("/add-event")
	@ResponseBody
	public void addApprenant(@RequestBody Event event)
	{
		eventService.addEvent(event);
	} 
	@DeleteMapping("/Delete-Event/{idEvent}")
	public void Delete_DisLike( @PathVariable("idEvent") Long idEvent) {
		 eventService.removeEvent(idEvent);
	}
	
	
	@PostMapping("/affect-UserEvent/{idEvent}/{idUser}")
	@ResponseBody
	public void affecterEventToUser( @PathVariable("idEvent") Long idEvent,@PathVariable("idUser") Long idUser) {
		//apprenantService.affecterApprenantFormation(idApprenant, idFormation);
		eventService.affecterEventToUser(idEvent, idUser);
	}

	  
	@DeleteMapping(path="removeEvent/{userId}/{EventId}")
	public void deleteCourse(@PathVariable("userId")Long userId,@PathVariable("EventId")Long EventId) {
		eventService.DeleteEvent(userId, EventId);
		
	}	
	
	  
	  
	
	  
	  
	  
	  
	
}
