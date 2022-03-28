package tn.esprit.spring.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;
import tn.esprit.spring.exceptions.CourseOwnerShip;
import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.service.courses.CourseChannelImpl;
@RestController
@Api(tags = "Channel")
@RequestMapping("api/ChannelStream")
public class ChannelCourseController {
	@Autowired
	CourseChannelImpl courseChannelImpl;
	@PostMapping("/createcourseonline/{courseId}")
	public void createchannel(@PathVariable("courseId")Long courseid,@ApiIgnore @AuthenticationPrincipal UserPrincipal u) throws IOException, InterruptedException {
	courseChannelImpl.createchannel(courseid, u.getId());
		
			
	}
	@PostMapping("/startStream/{courseId}")
	public void startchannel(@PathVariable("courseId")Long courseid,@ApiIgnore @AuthenticationPrincipal UserPrincipal u) throws IOException, InterruptedException {
	courseChannelImpl.startChannel(courseid,u.getId());
			
	}
	@PostMapping("/stopStream/{courseId}")
	public void stopchannel(@PathVariable("courseId")Long courseid,@ApiIgnore @AuthenticationPrincipal UserPrincipal u) throws IOException, InterruptedException {
	courseChannelImpl.stopChannel(courseid,u.getId());
			
	}
	
	@DeleteMapping("/deleteStream/{courseId}")
	public void deletechannel(@PathVariable("courseId")Long courseid,@ApiIgnore @AuthenticationPrincipal UserPrincipal u) throws IOException, InterruptedException, CourseOwnerShip {
		courseChannelImpl.deleteChannel(courseid,u.getId());
				
		}
	@GetMapping("/getChannel/{courseId}")
	public String getChannel(@PathVariable("courseId")Long courseid) throws IOException, InterruptedException {
		return courseChannelImpl.getChannelStatus(courseid).body();
				
		}
	
	
	

	
}
