package tn.esprit.spring.service.event;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

import tn.esprit.spring.config.TwilioConfiguration;
import tn.esprit.spring.entities.Donation;
import tn.esprit.spring.entities.Event;
import tn.esprit.spring.entities.Media;
import tn.esprit.spring.entities.SmsRequest;

import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.EventType;
import tn.esprit.spring.repository.EventRepo;
import tn.esprit.spring.repository.MediaRepo;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.service.user.ServiceAllEmail;
import tn.esprit.spring.serviceInterface.EventService;
import tn.esprit.spring.serviceInterface.SmsSender;

@Service
public class EventServiceImpl implements EventService{
	
	  private final SmsSender smsSender;
	
	@Autowired
	EventRepo eventRepo;
	@Autowired
	CloudinaryService cloudImage;
	 @Autowired
	    MediaRepo mediaRepo;
	 @Autowired
	 MediaService mediaService;
	@Autowired
	UserRepository userRepo;
	@Autowired
    ServiceAllEmail emailService;

	@Autowired
    public EventServiceImpl(@Qualifier("twilio") TwilioSmsSender smsSender) {
        this.smsSender = smsSender;
        
    }

	    
	/*@Override
	public void createEventbyUser(Long idUser , Event event)  throws MessagingException{
		Set<Event> eventList = new HashSet<Event>();
		
		eventList.add(event);
		User user = userRepo.findById(idUser).orElse(null);
	
		user.setCreatedEvents(eventList);
		userRepo.save(user);
		invite_participants (event);
		emailService.sendNewEventCreatedByUser(event.getEventName(), user.getEmail());
		
		
		
	}*/
    
	@Override
	public ResponseEntity<?> createEventbyUser(Long idUser, MultipartFile multipartFile, String EventName,
			String description,Date createAt, Date endAt, EventType typeEvent, int maxPlace, float targetDonation,String address)
			throws MessagingException, IOException {
		Set<User> usersList = new HashSet<User>();
		Set<Event> eventList = new HashSet<Event>();
		User user = userRepo.findById(idUser).orElse(null);
		BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
	      
	        
	        Map result = cloudImage.upload(multipartFile);
	        Event event = new Event();
	        event.setEventName(EventName);
	        event.setCreatedAt(createAt);
	        event.setEndAt(endAt);
	        event.setEventType(typeEvent);
	        event.setMaxPlace(maxPlace);
	        event.setTargetDonation(targetDonation);
	        event.setAddress(address);
	        event.setDescription(description);
	        
	        event.setCreateurEvent(user);
	        
	        
	        Media media =
	                new Media((String)result.get("original_filename"),
	                        (String)result.get("url"),
	                        (String)result.get("public_id"));
	       
	        media.setEvent(event);
	        eventList= user.getCreatedEvents();
	        eventList.add(event);
	        eventRepo.save(event);
	        user.setCreatedEvents(eventList);
	     
			userRepo.save(user);
	        mediaService.save(media);
	    	invite_participants (event);
			emailService.sendNewEventCreatedByUser(event.getEventName(), user.getEmail());
			
	
			return  new ResponseEntity(" the event was created with succeed ", HttpStatus.OK);
	}
	
	
	
	

		
	
	
	
	
	
	
	

	
	@Override
	public Event EditEventCreateByUser(Event e, Long idEvent) {
		
				Event Event = eventRepo.findById(idEvent).get();
				Event.setEventName(e.getEventName());
				Event.setEventType(e.getEventType());
				Event.setLink(e.getLink());
				Event.setDescription(e.getDescription());
				Event.setMaxPlace(e.getMaxPlace());
				Event.setTargetDonation(e.getTargetDonation());
		        Event.setAddress(e.getAddress());
				eventRepo.flush();
				return e;
	}
	

	
	@Override
	public ResponseEntity<?> updateImageForEvent(Long idmedia, MultipartFile multipartFile) throws IOException {
		Set<Media> mediaList = new HashSet<Media>();
	
		Media media = mediaRepo.findById(idmedia).orElse(null);
		
		BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if(bi == null){
            return new ResponseEntity("invalid image", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudImage.upload(multipartFile);
        media =
                new Media((String)result.get("original_filename"),
                        (String)result.get("url"),
                        (String)result.get("imagencode"));
       
        
        
    mediaRepo.flush();
    
      
		return new ResponseEntity(media, HttpStatus.OK);
	}


	
	
	


	@Override
	public ResponseEntity<?> deleteImageForEvent(Long idMedia) throws IOException {
		//Media media = mediaRepo.findById(idMedia).orElse(null);
		 if(!mediaService.exists(idMedia))
	            return new ResponseEntity("does not exist", HttpStatus.NOT_FOUND);
	       Media  media = mediaService.getOne(idMedia).get();
	        Map result = cloudImage.delete(media.getCodeImage());
	        mediaService.delete(idMedia);
	        return new ResponseEntity("imagen eliminada", HttpStatus.OK);
	}

	
	
	
	


	@Override
	public ResponseEntity<?> addImageForDocumentOfEvent(Long idEvent, Set<MultipartFile> multipartFile) throws IOException {
		return null;
		
	
	}
	
	
	public void invite_participants (Event event) throws MessagingException {
		
		//SmsRequest smsrequest = new SmsRequest(null, null);
		List<Long> CampanyList =  eventRepo.GET_LIST_CAMPANY();
		
		System.out.println("user campany");
		if(eventRepo.GET_LIST_CAMPANY() != null) {
			
			for (Long user : CampanyList) {
				User u = userRepo.findById(user).orElse(null);
			
			emailService.sendNewEventCreatedByUser(event.getEventName(), u.getEmail());
			//sendSms(smsrequest, u.getPhoneNumber(), event.getEventName()  + " : yatik Asba yahachlef");
			System.out.println("email envoye a"+ u.getName());
			}
		}
	/*	List<Long> ID_d = eventRepo.GET_ID_BEST_DONNER();
		System.out.println("user plus donneur");
		if(eventRepo.GET_ID_BEST_DONNER() != null)
		{
		for (Long id : ID_d) {
			
					System.out.println("no userbestdonnor");
			 
			User u = userRepo.findById(id).orElse(null);
			
			emailService.sendNewEventCreatedByUser(event.getEventName(), u.getEmail());
			
			System.out.println("email envoye a"+ u.getName());
		}
		}*/
	}
	
	 

	








	

	@Override
	public Long findUserDonationsById(Long id) {
		
		return eventRepo.findUserDonationsById(id);
	}
	
	
	
	


	@Override
	public void Participer_event(Long userid, Long eventId) {
		SmsRequest smsrequest = new SmsRequest(null, null);
		User u = userRepo.findById(userid).orElse(null);
		Event event = eventRepo.findById(eventId).orElse(null);
		if (event.getParticipants().size() < event.getMaxPlace() && userid != event.getCreateurEvent().getUserId()) {
		
			Set<Event> ev = u.getJoinedEvents();
			ev.add(event);
			u.setJoinedEvents(ev);
			userRepo.save(u);
			
			Set<User> user = event.getParticipants();
			user.add(u);
			event.setParticipants(user);
			event.setMaxPlace(event.getMaxPlace() -1);
			eventRepo.save(event);
			
			sendSms(smsrequest, u.getPhoneNumber(), event.getEventName()  + " : Participation avec succes");
			// evoie d un sms (Participation avec succes)
			System.out.println("Participation avec succes");
		}
			
		else {
		
		
//			sendSms(smsrequest, u.getPhoneNumber(), event.getEventName()  + " : il y a plus de place");
					//System.out.println("Place complets");
					System.out.println(event.getMaxPlace());
					System.out.println(event.getParticipants().size());
	}}

	
	
	@Override
	public void cancelparticipation(Long userid, Long eventId) {
		User user = userRepo.findById(userid).orElse(null);
		Event event = eventRepo.findById(eventId).orElse(null);
		user.getJoinedEvents().remove(event);
		userRepo.flush();
		
	}
	//arefaire



	

	@Override
	public String TargetAtrbut(Long eventId) {
		float s=0;
		Event e = eventRepo.findById(eventId).orElse(null);
		
		for (Donation d : e.getDonations()) {
			s=s+d.getAmount_forEvent();
						
		}
		if (s >= e.getTargetDonation())
			return "Objectif attente";
		else 
			return "object pas encore attendre";
					
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
	//e.setPlace(a);
	
	return eventRepo.save(e);
	}





  

	

	@Override
	public void sendSms(SmsRequest smsRequest,String numberPhone,String msg) {
		smsRequest.setPhoneNumber(numberPhone);
		smsRequest.setMessage(msg);
		smsSender.sendSms(smsRequest);
		
	}


//-----------------------------pagination--------------------------------------------------------------------------//

	@Override
	public Page<Event> findEventWithPaginationAndSorting(int offset, int pageSize, String field) {
		 Page<Event> events = eventRepo.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
	        return  events;
	}






	// --------------------------------crud simple non validé ----------------------------------


	@Override
	public void removeEvent(Long IdEvent) {
		eventRepo.deleteById(IdEvent);
		
	}
	
	
	@Override
	public List<Event> Get_all_Event() {
		return  eventRepo.findAll();

	}


	@Override
	public List<Long> GET_ID_BEST_DONNER() {
	
		return eventRepo.GET_ID_BEST_DONNER();
	}


	@Override
	public Event affecterEventToAddress(Long idEvent, String address) {
		Event event = eventRepo.findById(idEvent).orElse(null);
		
		return event;
	}







	



	
	//------------------------------------------------------------------------------------------------------









	








	



		
		
}
	

	


	
	

	











	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	



