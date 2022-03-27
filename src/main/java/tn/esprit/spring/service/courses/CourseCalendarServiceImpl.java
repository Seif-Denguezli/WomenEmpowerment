package tn.esprit.spring.service.courses;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nylas.Calendar;
import com.nylas.Calendars;
import com.nylas.Event;
import com.nylas.NylasAccount;
import com.nylas.NylasClient;
import com.nylas.Participant;
import com.nylas.RequestFailedException;

import tn.esprit.spring.entities.Certificate;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.repository.CertificateRepository;
import tn.esprit.spring.repository.CourseRepository;
@Service
public class CourseCalendarServiceImpl {
	@Autowired
	CourseRepository courseRepository;
	@Autowired
	CertificateRepository certificateRepository;
	public String createCal(long courseId) throws IOException, RequestFailedException {
		 Course c =courseRepository.findById(courseId).get();
		 NylasClient client = new NylasClient();
		  NylasAccount account = client.account("PfdQX6tyfrwPkqfO1z1dr6oAtxn7zD");
		  Calendars calendars = account.calendars();
		  Calendar newCal1 = new Calendar();
		  newCal1.setName(c.getCourseName());
		  newCal1.setDescription(c.getDomain().toString());
		  newCal1.setLocation("Remote");
		  newCal1.setTimezone("America/Los_Angeles");
		  Calendar created = calendars.create(newCal1);
		 c.setCalendarId(created.getId());
		 courseRepository.save(c);
		  return created.getId();
		  
	}
	
	
	 public void addEvent(Long courseId,String eventName,int hour,int minutes) throws IOException, RequestFailedException {
		 Course c =courseRepository.findById(courseId).get();
		 List<Certificate> certif = certificateRepository.findByCourse(courseId);
		  NylasClient client = new NylasClient();
		  NylasAccount account = client.account("PfdQX6tyfrwPkqfO1z1dr6oAtxn7zD");
		 // Calendars calendars = account.calendars();
		  Event.When when = null;
		  LocalDate today = LocalDate.now();
		  when = new Event.Date(today);
		  when = new Event.Datespan(today, today.plusDays(1));
		  Instant sixPmUtc = today.atTime(hour-1,minutes).toInstant(ZoneOffset.UTC);
		  when = new Event.Time(sixPmUtc);
		  when = new Event.Timespan(sixPmUtc, sixPmUtc.plus(1, ChronoUnit.HOURS));
		  
		  Event event = new Event(c.getCalendarId(),when);
		  
		 event.setWhen(when);
		  event.setTitle(eventName);
		  event.setLocation("Remote");
		  event.setDescription("Visit the course on the website Live section");
		  event.setBusy(true);
		  
		  List<Participant> participant = new ArrayList<Participant>();
		  for (Certificate certificate : certif) {
			 Participant p = new Participant(certificate.getUser().getEmail());
			 participant.add(p);
		}
		 
		  for(int i =0 ; i<participant.size();i++) {
			  event.setParticipants(participant);
		  }
		  
		  account.events().create(event, true);
		  
		  
		 }
}
