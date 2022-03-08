package tn.esprit.spring.serviceInterface.event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Transaction;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.service.event.EventService;

@Service
public class EventServiceImpl implements EventService{
	
	@Autowired
	EventRepo eventRepo;
	
	@Autowired
	UserRepository userRepo;

	@Override
	public void addEvent(Event event) {
		eventRepo.save(event);
		
	}

	@Override
	public Event editEvent(Event event) {
		eventRepo.saveAndFlush(event);
		return event;
	}

	

	@Override
	public void joinEvent(Long idUser, Long idEvent) {
		Set<Event> eventList = new HashSet<Event>();
		Set<User> userList = new HashSet<User>();
			
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
	

	@Override
	public Event deleteEvent(Long idUser, Long idEvent) {
		  User user = userRepo.findById(idUser).get();
			Event event = eventRepo.findById(idEvent).orElse(null);
			user.getJoinedEvents().remove(event);
			eventRepo.delete(event);
			return event ;
	}

	@Override
	public List<Event> Get_all_Event() {
		return  eventRepo.findAll();

	}

	@Override
	public void removeEvent(Long IdEvent) {
		eventRepo.deleteById(IdEvent);
		
	}

	@Override
	public Long retrieveMaxEventTransactioned() {
		
		return eventRepo.retrieveMaxEventTransactioned();
	}

	@Override
	public Long findUserDonationsById(Long id) {
		
		return eventRepo.findUserDonationsById(id);
	}

	

	


	
	

	
}










	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



