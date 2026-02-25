package fr.k4cook.dtos.user;

import java.time.LocalDateTime;

import fr.k4cook.entities.UserAccount;

/**
 * Data Transfer Object (DTO) for transferring user data between processes.
 * <p>
 * This DTO is specifically designed to encapsulate user account details such as
 * ID, first name,
 * last name, and email address. It is primarily used for sending user data from
 * the server to the client
 * in a structured format.
 */
public class UserResponseDto {

    /**
     * Unique identifier for the user.
     */
    private int id;

    /**
     * First name of the user.
     */
    private String firstName;

    /**
     * Last name of the user.
     */
    private String lastName;

    /**
     * Email address of the user.
     */
    private String email;

    private boolean isSuspended;

    private LocalDateTime suspendedTill;

    /**
     * Constructs a new {@code UserResponseDto} object using details from a
     * {@link UserAccount}.
     *
     * @param userAccount the {@code UserAccount} object containing the user's
     *                    account information
     */
    public UserResponseDto(UserAccount userAccount) {
        this.id = userAccount.getId();
        this.firstName = userAccount.getFirstName();
        this.lastName = userAccount.getLastName();
        this.email = userAccount.getEmail();
        this.isSuspended = LocalDateTime.now().isBefore(userAccount.getSuspendedTillDate());
        this.suspendedTill = userAccount.getSuspendedTillDate();
    }

    /**
     * Retrieves the unique identifier of the user.
     *
     * @return the user's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the user.
     *
     * @param id the new user ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the first name of the user.
     *
     * @return the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the last name of the user.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the email address of the user.
     *
     * @return the user's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email the new email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getSuspendedTill() {
        return suspendedTill;
    }

    public void setSuspendedTill(LocalDateTime suspendedTill) {
        this.suspendedTill = suspendedTill;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean isSuspended) {
        this.isSuspended = isSuspended;
    }
}
