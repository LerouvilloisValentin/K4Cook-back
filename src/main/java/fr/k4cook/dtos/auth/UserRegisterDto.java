package fr.k4cook.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Dto representing a Front request to register a User
 */
public class UserRegisterDto {
    /**
     * First name
     */
    @NotNull
    private String firstName;

    /**
     * Last name
     */
    @NotNull
    private String lastName;

    /**
     * Email
     * Must be in email formatting
     */
    @NotNull
    @Email
    private String email;

    /**
     * Password
     * Must have between 8 and 24 characters
     */
    @NotNull
    @Size(min = 8, max = 24)
    private String password;

    /**
     * Empty constructor
     */
    public UserRegisterDto() {
        super();
    }

    /**
     * Constructor with email and password
     * 
     * @param email    String
     * @param password String
     */
    public UserRegisterDto(String firstName, String lastName, String email, String password) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    /**
     * Get first name
     * 
     * @return String representing first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get last name
     * 
     * @return String representing last name
     */
    public String getLastName() {
        return lastName;
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
     * Get Password
     * 
     * @return String representing password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set first name
     * 
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set last name
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Set email
     * 
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set password
     * 
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
