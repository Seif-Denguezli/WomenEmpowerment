package tn.esprit.spring.serviceInterface.user;

import tn.esprit.spring.entities.PasswordResetToken;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.exceptions.EmailNotExist;
import tn.esprit.spring.exceptions.ResetPasswordException;
import tn.esprit.spring.exceptions.ResetPasswordTokenException;


public interface AuthenticationService
{
    User signInAndReturnJWT(User signInRequest);
	
    PasswordResetToken generatePasswordResetToken(String email) throws EmailNotExist;
	
	void updatePassword(String token, String newPassword) throws ResetPasswordException, ResetPasswordTokenException;
}
