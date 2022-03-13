package tn.esprit.spring.service.user;

import tn.esprit.spring.entities.PasswordResetToken;
import tn.esprit.spring.entities.User;
import tn.esprit.spring.exceptions.EmailNotExist;
import tn.esprit.spring.exceptions.ResetPasswordException;
import tn.esprit.spring.exceptions.ResetPasswordTokenException;
import tn.esprit.spring.repository.PasswordResetTokenRepository;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.security.UserPrincipal;
import tn.esprit.spring.security.jwt.JwtProvider;
import tn.esprit.spring.serviceInterface.user.AuthenticationService;
import tn.esprit.spring.serviceInterface.user.JwtRefreshTokenService;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.bytebuddy.utility.RandomString;


@Service
public class AuthenticationServiceImpl implements AuthenticationService
{
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtRefreshTokenService jwtRefreshTokenService;

    @Override
    public User signInAndReturnJWT(User signInRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
        );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        User signInUser = userPrincipal.getUser();
        signInUser.setAccessToken(jwt);
        signInUser.setRefreshToken(jwtRefreshTokenService.createRefreshToken(signInUser.getUserId()).getTokenId());

        return signInUser;
    }

	@Override
	public PasswordResetToken generatePasswordResetToken(String email) throws EmailNotExist {
		User user = userRepository.findByEmail(email).orElse(null);
		if (user!=null) {
			PasswordResetToken token = new PasswordResetToken();
			LocalDateTime nowDate = LocalDateTime.now();
			token.setCreateDate(nowDate);
			
			token.setUserId(user.getUserId());
			token.setToken(RandomString.make(45));
			token.setExprirationDate(nowDate.plusMinutes(15));
			
			passwordResetTokenRepository.save(token);
		}
		else {
			throw new EmailNotExist("Could not find any user related to the email : " + email);
		}
		return null;
	}


	@Override
	public void updatePassword(String token, String newPassword) throws ResetPasswordException, ResetPasswordTokenException{
		PasswordResetToken resetToken = passwordResetTokenRepository.findByToken(token);
		System.err.println(resetToken);
		User u = userRepository.findById(resetToken.getUserId()).orElse(null);
		if (token != null ) {
			
			if ( resetToken.getExprirationDate().isAfter(LocalDateTime.now() ) ) {
				
				String encodedPassword = passwordEncoder.encode(newPassword);
				u.setPassword(encodedPassword);
				userRepository.save(u);
			}
			
			else {
				throw new ResetPasswordTokenException("Reset Password Request has expired ! !");
			}

		}
		else {
			throw new ResetPasswordException("Error processing Reset Password Request !");
		}
		
	}
    

}
