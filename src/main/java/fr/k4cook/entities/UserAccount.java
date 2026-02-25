package fr.k4cook.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * Entity representing a UserAccount.
 */
@Entity
public class UserAccount {

    /** Id of the UserAccount */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Last name of the user */
    private String lastName;

    /** First name of the user */
    private String firstName;

    /** Email of the user */
    private String email;

    /** Password of the user */
    private String password;

    /** User is locked till this date */
    private LocalDateTime suspendedTillDate = LocalDateTime.now().minusDays(1);

    /** Authorities granted to the user */
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities;

    /** Messages created by the user */
    @OneToMany(mappedBy = "user")
    private List<Message> ownMessages = new ArrayList<>();

    /** Messages liked by the user */
    @ManyToMany
    @JoinTable(name = "USERS_LIKED_MESSAGES", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_MESSAGE"))
    private List<Message> likedMessages;

    /** Messages disliked by the user */
    @ManyToMany
    @JoinTable(name = "USERS_DISLIKED_MESSAGES", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_MESSAGE"))
    private List<Message> dislikedMessages;

    /**
     * Default constructor for the UserAccount class.
     */
    public UserAccount() {
        super();
    }

    /**
     * Constructor for creating a UserAccount with specified details.
     *
     * @param lastName  the last name of the user
     * @param firstName the first name of the user
     * @param email     the email of the user
     * @param password  the password of the user
     * @param roles     the roles of the user
     */
    public UserAccount(String lastName, String firstName, String email, String password, String... roles) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.authorities = Arrays.asList(roles).stream()
                .map(String::new)
                .map(String.class::cast)
                .toList();
    }

    public boolean isSuspended() {
        return LocalDateTime.now().isBefore(suspendedTillDate);
    }

    /**
     * Gets the ID of the user.
     *
     * @return the ID of the user
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the user.
     *
     * @param id the new ID of the user
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the last name of the user.
     *
     * @return the last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName the new last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the first name of the user.
     *
     * @return the first name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName the new first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     *
     * @param email the new email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password the new password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the authorities granted to the user.
     *
     * @return the authorities granted to the user
     */
    public List<String> getAuthorities() {
        return authorities;
    }

    /**
     * Sets the authorities granted to the user.
     *
     * @param authorities the new authorities granted to the user
     */
    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    /**
     * Gets the messages created by the user.
     *
     * @return the messages created by the user
     */
    public List<Message> getOwnMessages() {
        return ownMessages;
    }

    /**
     * Sets the messages created by the user.
     *
     * @param messages the new messages created by the user
     */
    public void setOwnMessages(List<Message> messages) {
        this.ownMessages = messages;
    }

    /**
     * Gets the messages liked by the user.
     *
     * @return the messages liked by the user
     */
    public List<Message> getLikedMessages() {
        return likedMessages;
    }

    /**
     * Sets the messages liked by the user.
     *
     * @param messages the new messages liked by the user
     */
    public void setLikedMessages(List<Message> likedMessages) {
        this.likedMessages = likedMessages;
    }

    /**
     * Add the message liked by the user.
     *
     * @param message the new message liked by the user
     */
    public void addLikedMessage(Message message) {
        likedMessages.add(message);
    }

    /**
     * Remove the message liked by the user.
     *
     * @param message the message unliked by the user
     */
    public void removeLikedMessage(Message message) {
        likedMessages.remove(message);
    }

    /**
     * Gets the messages disliked by the user.
     *
     * @return the messages disliked by the user
     */
    public List<Message> getDislikedMessages() {
        return dislikedMessages;
    }

    /**
     * Sets the messages disliked by the user.
     *
     * @param dislikedMessages the messages disliked by the user
     */
    public void setDislikedMessages(List<Message> dislikedMessages) {
        this.dislikedMessages = dislikedMessages;
    }

    /**
     * Add the message disliked by the user.
     *
     * @param message the new message disliked by the user
     */
    public void addDislikedMessage(Message message) {
        dislikedMessages.add(message);
    }

    /**
     * Remove the message disliked by the user.
     *
     * @param message the message undisliked by the user
     */
    public void removeDislikedMessage(Message message) {
        dislikedMessages.add(message);
    }

    /**
     * Get SuspendedTillDate
     * 
     * @return LocaldateTime
     */
    public LocalDateTime getSuspendedTillDate() {
        return suspendedTillDate;
    }

    /**
     * Set SuspendedTilldate
     * 
     * @param suspendedTillDate
     */
    public void setSuspendedTillDate(LocalDateTime suspendedTillDate) {
        this.suspendedTillDate = suspendedTillDate;
    }
}
