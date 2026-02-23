package fr.k4cook.K4Cook.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

/**
 * Dto usually send from front to log an user
 */
public class UserLoginDto {
    /**
     * Email
     */
    @NotNull
    @Email
    private String email;

    /**
     * Password
     */
    @NotNull
    private String password;

    /**
     * Empty constructor
     */
    public UserLoginDto() {
        super();
    }

    /**
     * Contrcutor with email and password
     * 
     * @param email    String
     * @param password String
     */
    public UserLoginDto(String email, String password) {
        super();
        this.email = email;
        this.password = password;
    }

    /**
     * Get email
     * 
     * @return String representing email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Get password
     * 
     * @return String representing password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set email
     * 
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set password
     * 
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
