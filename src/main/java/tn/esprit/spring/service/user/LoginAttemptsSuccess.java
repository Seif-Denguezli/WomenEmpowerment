package tn.esprit.spring.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import tn.esprit.spring.entities.User;

@Component
public class LoginAttemptsSuccess {
    private LoginAttempts loginAttemptService;

    @Autowired
    public LoginAttemptsSuccess(LoginAttempts loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();
        if(principal instanceof User) {
            User user = (User) event.getAuthentication().getPrincipal();
            loginAttemptService.RemoveUserAttemptFromCache(user.getUsername());
        }
    }
}