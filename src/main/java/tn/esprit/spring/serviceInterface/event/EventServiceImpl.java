package tn.esprit.spring.serviceInterface.event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.UserRepo;
import tn.esprit.spring.service.event.EventService;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	EventRepo eventRepo;
	
	@Autowired
	UserRepo userRepo;

	@Override
	public void addEvent(Event event) {
		
		eventRepo.save(event);
	}

	@Override
	public ResponseEntity<?> Update_Event(Event event, Long idEvent, Long idUser) {
		List<User> ListUser = new ArrayList<User>();
		if (eventRepo.existsById(idEvent)) {
			
		
			Event event1 = 	eventRepo.findById(idEvent).orElseThrow(
					 ()->new EntityNotFoundException("event not found")
							 );
			User user = 	userRepo.findById(idUser).orElseThrow(
					 ()->new EntityNotFoundException("User not found")
							 );
		
			 for (User user2 : ListUser) {
			 				 if (user2.equals(user)) {
					 event1.getEventName();
					 event1.getCreatedAt();
					 event1.getEventName();	
					 
					 eventRepo.save(event1);
					 return ResponseEntity.ok().body(event1);
			   }
			 
			else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body("No permission to delete this Event ");
				}
			 
			 	}
		}
		else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("event Not Founf");
			 
		}
		return null;
		
		
	}
		






	
	
	@Override
	public void removeEvent(Long IdEvent) {
		//Event e = eventRepo.findById(IdEvent).orElse(null);

		eventRepo.deleteById(IdEvent);
		
	}



@Override
public Event deletDoation(Long idUser, Long idEvent) {
	  User usr = userRepo.findById(idUser).get();
		Event e = eventRepo.findById(idEvent).orElse(null);
		usr.getCreatedEvents().remove(e);
		eventRepo.delete(e);
		return e ;
}

@Override
public void affecterEventToUser(Long idEvent, Long idUser) {
	List<Event> eventList = new ArrayList<Event>();
	List<User> userList = new ArrayList<User>();
		
	Event event = eventRepo.findById(idEvent).orElse(null);
	event.setEventId(idEvent);
	
	
	User user = userRepo.findById(idUser).orElse(null);
	user.setUserId(idUser);;
	
	eventList.add(event);
	userList.add(user);
	user.setCreatedEvents(eventList);
	event.setParticipants(userList);
	//projectRepo.save(project);
	
	eventRepo.saveAndFlush(event);
	
	
}







}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



