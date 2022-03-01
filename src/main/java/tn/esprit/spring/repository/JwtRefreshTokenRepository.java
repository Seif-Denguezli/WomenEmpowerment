package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.spring.entities.JwtRefreshToken;

/**
 * @author sa
 * @date 29.10.2021
 * @time 13:39
 */
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String>
{
}
