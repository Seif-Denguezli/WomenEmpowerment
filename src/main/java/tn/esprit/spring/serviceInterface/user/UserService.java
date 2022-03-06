package tn.esprit.spring.serviceInterface.user;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Notification;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;
import tn.esprit.spring.exceptions.EmailExist;
import tn.esprit.spring.exceptions.UsernameExist;
import tn.esprit.spring.exceptions.UsernameNotExist;


public interface UserService
{
    User saveUser(User user) throws UsernameNotExist, UsernameExist, EmailExist, MessagingException;

    Optional<User> findByUsername(String username);
    
	Optional<User> findByEmail(String email);

    void changeRole(Role newRole, String username);
    
    void makeAdmin(String username);

    List<User> findAllUsers();
    
    List<User> findSubscribedUsers();
    
    List<Notification> findNotificationsByUser(Long userId);
    
    Notification addNotification(Notification notification, String username);
    
    void deleteNotification(Long notificationId);
    
    List<Notification> findAllNotifications();
    
    List<Course> findCoursesBetweenDates(Date startDate, Date endDate);
    
    Subscription addSubscription(Subscription s, String username);
    
    void extendSubscription(String username, int nbMonths);
    
    void removeSubcription(String username);


    
}
