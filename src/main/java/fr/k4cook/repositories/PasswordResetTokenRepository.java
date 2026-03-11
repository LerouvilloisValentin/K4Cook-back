package fr.k4cook.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import fr.k4cook.entities.PasswordResetToken;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);
}