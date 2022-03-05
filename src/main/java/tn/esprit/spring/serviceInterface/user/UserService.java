package tn.esprit.spring.serviceInterface.user;



import java.util.Date;
import java.util.List;
import java.util.Optional;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Notification;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;


public interface UserService
{
    User saveUser(User user);

    Optional<User> findByUsername(String username);

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
