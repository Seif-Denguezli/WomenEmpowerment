package tn.esprit.spring.controllers;



import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Notification;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.serviceInterface.user.UserService;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/user")//pre-path
public class UserController
{
    @Autowired
    private UserService userService;
    
    
    @GetMapping("/notifications")
    public List<Notification> findNotificationsByUser(@AuthenticationPrincipal UserPrincipal u) {
    	return userService.findNotificationsByUser(u.getId());
    }
    
    @PostMapping("/notification/save/{username}")
    public Notification addNotification(@RequestBody Notification notification,@PathVariable(name="username") String username) {
    	return userService.addNotification(notification, username);
    }
    
    @DeleteMapping("/notification/delete/{notificationId}")
    public void deleteNotification(@PathVariable(name="notificationId") Long notificationId) {
    	userService.deleteNotification(notificationId);
    }
    
    @GetMapping("/notifications/all")
    public List<Notification> findAllNotifications() {
    	return userService.findAllNotifications();
    }
    
    @GetMapping("/courses/{startDate}/{endDate}")
    public List<Course> findCoursesBetweenDates(@PathVariable(name="startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")Date startDate,
    		@PathVariable(name="endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
    	return userService.findCoursesBetweenDates(startDate, endDate);
    }
    
    @PostMapping("/subscription/save/{username}")
    public Subscription addSubscription(@RequestBody Subscription s,@PathVariable(name="username") String username) {
    	return userService.addSubscription(s, username);
    }
    
    @PutMapping("/subscription/extend/{username}/{nbMonths}")
    public void extendSubscription(@PathVariable(name="username") String username,@PathVariable(name="nbMonths") int nbMonths) {
    	userService.extendSubscription(username, nbMonths);
    }
    
    @DeleteMapping("/subscription/remove")
    public void removeSubcription(String username) {
    	userService.removeSubcription(username);
    }
    
    @GetMapping("/subscribed/all")
    public List<User> findSubscribedUsers() {
    	return userService.findSubscribedUsers();
    }



    
}
