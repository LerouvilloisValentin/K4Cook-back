package fr.k4cook.entities;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount user;

    public PasswordResetToken() {
    }

    public PasswordResetToken(String token, UserAccount user, LocalDateTime expirationDate) {
        this.token = token;
        this.user = user;
        this.expirationDate = expirationDate;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public UserAccount getUser() {
        return user;
    }
}
