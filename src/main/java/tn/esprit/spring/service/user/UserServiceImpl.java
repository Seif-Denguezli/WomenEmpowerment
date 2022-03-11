package tn.esprit.spring.service.user;


import org.apache.commons.lang3.StringUtils;
import org.cryptacular.bean.EncodingHashBean;
import org.cryptacular.spec.CodecSpec;
import org.cryptacular.spec.DigestSpec;
import org.passay.CharacterRule;
import org.passay.DigestHistoryRule;
import org.passay.EnglishCharacterData;
import org.passay.EnglishSequenceData;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Notification;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.enumerations.Role;
import tn.esprit.spring.exceptions.EmailExist;
import tn.esprit.spring.exceptions.PasswordValidException;
import tn.esprit.spring.exceptions.UsernameExist;
import tn.esprit.spring.exceptions.UsernameNotExist;
import tn.esprit.spring.repository.CourseRepository;
import tn.esprit.spring.repository.NotificationRepository;
import tn.esprit.spring.repository.SubscriptionRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.serviceInterface.user.UserService;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.MessagingException;


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
    
    @Autowired
    ServiceAllEmail emailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) throws UsernameNotExist, UsernameExist, EmailExist, MessagingException
    {
    	isvalidUsernameAndEmail(EMPTY, user.getUsername(), user.getEmail());
    	isValid(user.getPassword());
        user.setRole(Role.USER);
        emailService.sendNewPasswordEmail(user.getName(), user.getPassword(), user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username)
    { 
        return userRepository.findByUsername(username);
    }
    
    @Override
    public Optional<User> findByEmail(String email)
    {
        return userRepository.findByEmail(email);
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
	
	
	 private User isvalidUsernameAndEmail(String currentUsername, String newUsername, String newEmail) 
			 throws UsernameNotExist, UsernameExist, EmailExist {
	        User userByNewUsername = findByUsername(newUsername).orElse(null);
	        User userByNewEmail = findByEmail(newEmail).orElse(null);
	        if(StringUtils.isNotBlank(currentUsername)) {
	            User currentUser = findByUsername(currentUsername).orElse(null);
	            if(currentUser == null) {
	                throw new UsernameNotExist("No user found by username: " + currentUsername);
	            }
	            if(userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())) {
	                throw new UsernameExist("Username already exists");
	            }
	            if(userByNewEmail != null && !currentUser.getUserId().equals(userByNewEmail.getUserId())) {
	                throw new EmailExist("Email are already exists");
	            }
	            return currentUser;
	        } else {
	            if(userByNewUsername != null) {
	                throw new UsernameExist("Username already exists");
	            }
	            if(userByNewEmail != null) {
	                throw new EmailExist("Email are already exists");
	            }
	            return null;
	        }
	    }
	 
	 @SneakyThrows
	 public boolean isValid(String password) {
		 String messageTemplate = null;
		 Properties props = new Properties();
		 InputStream inputStream = getClass().getClassLoader().getResourceAsStream("passay.properties");
		 try {
			 props.load(inputStream);
		 } catch (IOException e) {
			 e.printStackTrace();
	     	}
		 MessageResolver resolver = new PropertiesMessageResolver(props);


		 List<PasswordData.Reference> history = Arrays.asList(
				 // Password=P@ssword1
				 new PasswordData.HistoricalReference(
	                        "SHA256",
	                        "j93vuQDT5ZpZ5L9FxSfeh87zznS3CM8govlLNHU8GRWG/9LjUhtbFp7Jp1Z4yS7t"),

	                // Password=P@ssword2
				 new PasswordData.HistoricalReference(
	                        "SHA256",
	                        "mhR+BHzcQXt2fOUWCy4f903AHA6LzNYKlSOQ7r9np02G/9LjUhtbFp7Jp1Z4yS7t"),

	                // Password=P@ssword3
				 new PasswordData.HistoricalReference(
	                        "SHA256",
	                        "BDr/pEo1eMmJoeP6gRKh6QMmiGAyGcddvfAHH+VJ05iG/9LjUhtbFp7Jp1Z4yS7t")
	        );
	        EncodingHashBean hasher = new EncodingHashBean(
	                new CodecSpec("Base64"), // Handles base64 encoding
	                new DigestSpec("SHA256"), // Digest algorithm
	                1, // Number of hash rounds
	                false); // Salted hash == false

	        PasswordValidator validator = new PasswordValidator(resolver, Arrays.asList(

	                // length between 8 and 16 characters
	                new LengthRule(8, 16),

	                // at least one upper-case character
	                new CharacterRule(EnglishCharacterData.UpperCase, 1),

	                // at least one lower-case character
	                new CharacterRule(EnglishCharacterData.LowerCase, 1),

	                // at least one digit character
	                new CharacterRule(EnglishCharacterData.Digit, 1),

	                // at least one symbol (special character)
	                new CharacterRule(EnglishCharacterData.Special, 1),


	                // no whitespace
	                new WhitespaceRule(),

	                // rejects passwords that contain a sequence of >= 3 characters alphabetical  (e.g. abc, ABC )
	                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 3, false),
	                // rejects passwords that contain a sequence of >= 3 characters numerical   (e.g. 123)
	                new IllegalSequenceRule(EnglishSequenceData.Numerical, 3, false)

	                ,new DigestHistoryRule(hasher)

	               ));

	        RuleResult result = validator.validate(new PasswordData(password));


	        PasswordData data = new PasswordData("P@ssword1", password);//"P@ssword1");
	        data.setPasswordReferences(history);
	        RuleResult result2 = validator.validate(data);


	        if (result.isValid() ) {
	            return true;
	        }
	        try {
	            if (result.isValid()==false) {
	                List<String> messages = validator.getMessages(result);

	                messageTemplate = String.join(",", messages);

	                System.out.println("Invalid Password: " + validator.getMessages(result));
	                }
	               } finally {
	            throw new PasswordValidException(messageTemplate);

	        }

	    }


}
