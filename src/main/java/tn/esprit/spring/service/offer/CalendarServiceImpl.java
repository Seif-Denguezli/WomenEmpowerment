package tn.esprit.spring.service.offer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nylas.NylasAccount;
import com.nylas.NylasClient;
import com.nylas.RequestFailedException;

import tn.esprit.spring.serviceInterface.offer.ICalendarService;

import com.nylas.Calendar;
import com.nylas.CalendarQuery;
import com.nylas.Calendars;
import com.nylas.Event;
import com.nylas.EventQuery;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;


import com.nylas.Participant;

import com.nylas.Event.Timespan;
@Service
public class CalendarServiceImpl {
	
	@Autowired 
	ICalendarService calendarservice;
	
	public String createCal() throws IOException, RequestFailedException {
		 NylasClient client = new NylasClient();
		  NylasAccount account = client.account("wOIy4UmmlWREVdr9S4pH5kAzRprvax");
		  Calendar newCal = new Calendar();
		  newCal.setName("New Test Calendar");
		  newCal.setDescription("Testing calendar creation");
		  newCal.setLocation("far, far away");
		  newCal.setTimezone("America/Los_Angeles");
		  Calendars calendars = account.calendars();

		 
		  // Add Metadata

		  Calendar newCal1 = new Calendar();
		  newCal1.setName("New Test Calendar");
		  newCal1.setDescription("Testing calendar creation");
		  newCal1.setLocation("far, far away");
		  newCal1.setTimezone("America/Los_Angeles");
		  Calendar created = calendars.create(newCal);
		  return created.getId();
	}
 public void postEventExample() throws IOException, RequestFailedException {
  NylasClient client = new NylasClient();
  NylasAccount account = client.account("wOIy4UmmlWREVdr9S4pH5kAzRprvax");
  Calendar newCal = new Calendar();
  newCal.setName("New Test Calendar");
  newCal.setDescription("Testing calendar creation");
  newCal.setLocation("far, far away");
  newCal.setTimezone("America/Los_Angeles");
  Calendars calendars = account.calendars();

 
  // Add Metadata

  Calendar newCal1 = new Calendar();
  newCal1.setName("New Test Calendar");
  newCal1.setDescription("Testing calendar creation");
  newCal1.setLocation("far, far away");
  newCal1.setTimezone("America/Los_Angeles");
  Calendar created = calendars.create(newCal);
  // The event "when" (date/time) can be set as one of 4 types.
  // For details: https://docs.nylas.com/reference#event-subobjects
  Event.When when = null;
  LocalDate today = LocalDate.now();
  when = new Event.Date(today);
  when = new Event.Datespan(today, today.plusDays(1));
  Instant sixPmUtc = today.atTime(18, 0).toInstant(ZoneOffset.UTC);
  when = new Event.Time(sixPmUtc);
  when = new Event.Timespan(sixPmUtc, sixPmUtc.plus(1, ChronoUnit.HOURS));
    
  // Create a new event object
  // Provide the appropriate id for a calendar to add the event to a specific calendar
  Event event = new Event("4ivyb2plt2wbt028118j0etz6",when);

  // save() must be called to save the event to the third party provider
  // notifyParticipants='true' will send a notification email to
  // all email addresses specified in the participants
  account.events().create(event, true);

  event.setTitle("Party!");
  event.setLocation("My House!");
  event.setDescription("Let's celebrate our calendar integration!!");
  event.setBusy(true);

  // Metadata can be added to an event to store extra information and
  // add custom fields. You can also query on metadata by keys, values, and key-value pairs


  // Participants are added as a list of Participant objects, which require email
  // and may contain name, status, or comment as well
  event.setParticipants(Arrays.asList(new Participant("swag@nylas.com").name("My Nylas Friend")));

  // Update the event with the new values and notify the participants
  account.events().update(event, true);
  Event event1 = account.events().create(event, true);
 }
 }

  // Use events().create() to save the event to the third party provider
  // 2nd argument is a boolean notification to send to all participants
  
   
 