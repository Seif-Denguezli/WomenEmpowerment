package tn.esprit.spring.serviceInterface.user;



import tn.esprit.spring.entities.JwtRefreshToken;
import tn.esprit.spring.entities.User;

/**
 * @author sa
 * @date 29.10.2021
 * @time 13:41
 */
public interface JwtRefreshTokenService
{
    JwtRefreshToken createRefreshToken(Long userId);

    User generateAccessTokenFromRefreshToken(String refreshTokenId);
}
