package tn.esprit.spring.service.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Notification;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.NotificationRepository;
import tn.esprit.spring.repository.SubscriptionRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.user.UserService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    NotificationRepository notificationRepository;
    
    @Autowired
    CourseRepository courseRepository;
    
    @Autowired
    SubscriptionRepository subscriptionRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user)
    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
      

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username)
    {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional //Transactional is required when executing an update/delete query.
    public void changeRole(Role newRole, String username)
    {
        userRepository.updateUserRole(username, newRole);
    }

    @Override
    public List<User> findAllUsers()
    {
        return userRepository.findAll();
    }

	@Override
	@Transactional //Transactional is required when executing an update/delete query.
	public void makeAdmin(String username) {
		userRepository.makeAdmin(username);
		
	}

	@Override
	public List<Notification> findNotificationsByUser(Long userId) {
		return notificationRepository.userNotification(userId);
	}

	@Override
	public Notification addNotification(Notification notification, String username) {
		User user = userRepository.findByUsername(username).get();
		notification.setRead(false);
		notification.setUser(user);
		return notificationRepository.save(notification);
	}

	@Override
	public void deleteNotification(Long notificationId) {
		Notification notif = notificationRepository.findById(notificationId).orElse(null);
		notificationRepository.delete(notif);
		
	}

	@Override
	public List<Notification> findAllNotifications() {
		// TODO Auto-generated method stub
		return notificationRepository.findAll();
	}

	@Override
	public List<Course> findCoursesBetweenDates(Date startDate, Date endDate) {
		List<Course> courses = courseRepository.findAll();
		List<Course> availableCourses = new ArrayList<>();
		for (Course c : courses) {
			if (c.getStartDate().compareTo(startDate) < 0 && c.getStartDate().compareTo(endDate) > 0)
				availableCourses.add(c);
		}
		return availableCourses;
	}

	@Override
	public List<User> findSubscribedUsers() {
		return userRepository.subscribedUsers();
	}

	@Override
	public Subscription addSubscription(Subscription s, String username) {
		Subscription sub =  subscriptionRepository.save(s);
		User u = userRepository.findByUsername(username).orElse(null);
		u.setSubscription(sub);
		userRepository.save(u);
		return sub;
	}

	@Override
	public void extendSubscription(String username, int nbMonths) {
		User u = userRepository.findByUsername(username).orElse(null);
		Subscription s = u.getSubscription();
		Calendar c = Calendar.getInstance();
		c.setTime(s.getExpiresAt());
		c.add(Calendar.MONTH, nbMonths);
		Date date = c.getTime();
		u.getSubscription().setExpiresAt(date);
		userRepository.save(u);
		
	}

	@Override
	public void removeSubcription(String username) {
		User u = userRepository.findByUsername(username).orElse(null);
		Subscription s = u.getSubscription();
		u.setSubscription(null);
		subscriptionRepository.delete(s);
	}
	
	@Scheduled(cron = "*/30 * * * * *")
	public void nbreUnreadNotifications() {
		int x = notificationRepository.userNotification(3L).size();
		log.info("Unread notifs : " + x);
	}


}
