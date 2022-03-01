package tn.esprit.spring.security.jwt;

import org.springframework.security.core.Authentication;

import tn.esprit.spring.security.UserPrincipal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sa
 * @date 29.10.2021
 * @time 13:01
 */
public interface JwtProvider
{
    String generateToken(UserPrincipal auth);

    Authentication getAuthentication(HttpServletRequest request);

    boolean isTokenValid(HttpServletRequest request);
}
