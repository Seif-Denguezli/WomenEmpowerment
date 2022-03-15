package tn.esprit.spring.serviceInterface.event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import tn.esprit.spring.config.TwilioConfiguration;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.SmsRequest;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.service.event.EventService;
import tn.esprit.spring.service.event.SmsSender;
import tn.esprit.spring.service.user.ServiceAllEmail;

@Service
public class EventServiceImpl implements EventService{
	
	  private final SmsSender smsSender;
	
	@Autowired
	EventRepo eventRepo;
	
	@Autowired
	UserRepository userRepo;
	@Autowired
    ServiceAllEmail emailService;

	@Autowired
    public EventServiceImpl(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
    }

	    

	 
	 
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
	public void removeEvent(Long IdEvent) {
		eventRepo.deleteById(IdEvent);
		
	}
	
	
	@Override
	public List<Event> Get_all_Event() {
		return  eventRepo.findAll();

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
	public Long findUserDonationsById(Long id) {
		
		return eventRepo.findUserDonationsById(id);
	}

	@Override
	public void Participer_event(Long userid, Long eventId) {
		User u = userRepo.findById(userid).orElse(null)		;
		Event e = eventRepo.findById(eventId).orElse(null)		;
		if (e.getParticipants().size() == e.getMaxPlace()) {
			// evoie d un sms (nbr des places rt3abew)
			System.out.println("Place complets");
			
		}
			
		else {
		
		
		Set<Event> le = u.getJoinedEvents();
		le.add(e);
		u.setJoinedEvents(le);
		userRepo.save(u);
		
		Set<User> lu = e.getParticipants();
		lu.add(u);
		e.setParticipants(lu);
		eventRepo.save(null);
		
		
		// evoie d un sms (Participation avec succes)
		System.out.println("Participation avec succes");
	}}
	
	public String TargetAtrbut(Long eventId) {
		float s=0;
		Event e = eventRepo.findById(eventId).orElse(null)		;
		
		for (Donation d : e.getDonations()) {
			s=s+d.getAmount_forEvent();
						
		}
		if (s >= e.getTargetDonation())
			return "Objectif attente";
		else 
			return "object pas encore attendre";
					
		
	}
	
	
	public   void invite_participants (Event event) throws MessagingException {
		SmsRequest smsrequest = new SmsRequest(null, null);
		List<Long> CampanyList =  eventRepo.GET_LIST_CAMPANY();
		System.out.println("user campany");
		for (Long user : CampanyList) {
			User u = userRepo.findById(user).orElse(null);
			
			emailService.sendNewEventCreatedByUser(event.getEventName(), u.getEmail());
			sendSms(smsrequest, u.getPhoneNumber(), event.getEventName()  + " : void vvotre mail");
			System.out.println("email envoye a"+ u.getName());
		}
		List<Long> ID_d = eventRepo.GET_ID_BEST_DONNER();
		System.out.println("user plus donneur");
		for (Long long1 : ID_d) {
			User u = userRepo.findById(long1).orElse(null);
			emailService.sendNewEventCreatedByUser(event.getEventName(), u.getEmail());
			System.out.println("email envoye a"+ u.getName());
		}
		
	}
	
	public Event affecte_place_event_byavie(Long id_event) {
		List<String > avie =new  ArrayList();
		avie.add("sousse");avie.add("monastir");avie.add("monastir");avie.add("monastir");avie.add("mahdia");avie.add("mahdia");avie.add("mahdia");avie.add("mahdia");
		String a = null;
		int x =0;
		int y =0;
	for (String string : avie) {
		 y =0;
		for (String string2 : avie) {
			if (string2.equals(string))
				y ++;}
		if (y>x)  {  x = y ; a = string;}
		}
	Event e = eventRepo.findById(id_event).orElse(null);
	e.setPlace(a);
	return eventRepo.save(e);
	}

	@Override
	public void createEventbyUser(Long iduser, Event event) throws MessagingException {
		Set<Event> eventList = new HashSet<Event>();
		eventList.add(event);
		User user = userRepo.findById(iduser).orElse(null);
		
		user.setCreatedEvents(eventList);
		userRepo.save(user);
		invite_participants (event);
		emailService.sendNewEventCreatedByUser(event.getEventName(), user.getEmail());
		
		
		
	}





	
	@Override
	public void sendSms(SmsRequest smsRequest,String nb,String msg) {
		smsRequest.setPhoneNumber(nb);
		smsRequest.setMessage(msg);
		smsSender.sendSms(smsRequest);
		
	}


















	



		
		
}
	

	


	
	

	











	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



