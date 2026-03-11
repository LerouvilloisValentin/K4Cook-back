package fr.k4cook.services;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.k4cook.entities.PasswordResetToken;
import fr.k4cook.entities.UserAccount;
import fr.k4cook.repositories.PasswordResetTokenRepository;
import fr.k4cook.repositories.UserAccountRepository;

public class PasswordResetService {

    @Autowired
    private UserAccountRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // register user with a new token
    public void createPasswordResetToken(String email) {
        UserAccount user = userRepository.findByEmail(email);
        if (user == null) {
            return;
        }
        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken(
                token, user, LocalDateTime.now().plusMinutes(15));
        tokenRepository.save(resetToken);
        System.out.println("Reset link : http://localhost:4200/reset-password?token=" + token);
    };

    public void resetPassword(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null) {
            throw new RuntimeException("Invalid token");
        }
        if (resetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        UserAccount user = resetToken.getUser();
        System.out.println("user" + user);
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        tokenRepository.delete(resetToken);
    }
}
