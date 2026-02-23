package fr.k4cook.K4Cook.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * Represents a message entity with an ID, likes, dislikes, date, section, and
 * parent message.
 */
@Entity
public class Message {

    /** Id of the message */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Content */
    @Column(name = "CONTENT")
    private String content;

    /** Date and time when the message was created */
    @Column(name = "DATE_TIME")
    private LocalDateTime date;


    /** The parent message, if this message is a reply */
    @ManyToOne
    @JoinColumn(name = "ID_PARENT_MESSAGE")
    @JsonIgnoreProperties("childrenMessages")
    private Message parentMessage;

    /** The children messages */
    @OneToMany(mappedBy = "parentMessage")
    @JsonIgnoreProperties("parentMessage")
    private List<Message> childrenMessages = new ArrayList<>();

    /** User that have liked this message */
    @ManyToMany
    @JoinTable(name = "USERS_LIKED_MESSAGES", joinColumns = @JoinColumn(name = "ID_MESSAGE"), inverseJoinColumns = @JoinColumn(name = "ID_USER"))
    private List<UserAccount> usersLiked = new ArrayList<>();

    /** User that have disliked this message */
    @ManyToMany
    @JoinTable(name = "USERS_DISLIKED_MESSAGES", joinColumns = @JoinColumn(name = "ID_MESSAGE"), inverseJoinColumns = @JoinColumn(name = "ID_USER"))
    private List<UserAccount> usersDisliked = new ArrayList<>();

    /** The user who posted the message */
    @ManyToOne
    @JoinColumn(name = "ID_USER")
    @JsonIgnoreProperties({ "sections", "password", "authorities", "notifications", "messages" })
    private UserAccount user;

    /**
     * Default constructor for the Message class.
     */
    public Message() {
    }

    /**
     * Gets the ID of the message.
     *
     * @return the ID of the message
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the message.
     *
     * @param id the new ID of the message
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the date and time when the message was created.
     *
     * @return the creation date and time
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date and time when the message was created.
     *
     * @param date the new creation date and time
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Gets the parent message, if this message is a reply.
     *
     * @return the parent message
     */
    public Message getParentMessage() {
        return parentMessage;
    }

    /**
     * Sets the parent message, if this message is a reply.
     *
     * @param message the new parent message
     */
    public void setParentMessage(Message message) {
        this.parentMessage = message;
    }

    /**
     * Gets the user who posted the message.
     * 
     * @return the user
     */
    public UserAccount getUser() {
        return user;
    }

    /**
     * Sets the user who posted the message.
     * 
     * @param user the user to set
     */
    public void setUser(UserAccount user) {
        this.user = user;
    }

    /**
     * Get children messages
     * 
     * @return List of Message
     */
    public List<Message> getChildrenMessages() {
        return childrenMessages;
    }

    /**
     * Set children messages
     * 
     * @param childrenMessages
     */
    public void setChildrenMessages(List<Message> childrenMessages) {
        this.childrenMessages = childrenMessages;
    }

    /**
     * Gets Users who liked this message
     * 
     * @return List of UserAccount
     */
    public List<UserAccount> getUsersLiked() {
        return usersLiked;
    }

    /**
     * Gets Users who disliked this message
     * 
     * @return List of UserAccount
     */
    public List<UserAccount> getUsersDisliked() {
        return usersDisliked;
    }

    /**
     * Sets Users who liked this message
     * 
     * @param usersLiked List of UserAccount
     */
    public void setUsersLiked(List<UserAccount> usersLiked) {
        this.usersLiked = usersLiked;
    }

    /**
     * Sets Users who disliked this message
     * 
     * @param usersLiked List of UserAccount
     */
    public void setUsersDisliked(List<UserAccount> usersDisliked) {
        this.usersDisliked = usersDisliked;
    }

    /**
     * Adds User who liked this message
     * 
     * @param userAccount UserAccount
     */
    public void addUsersLiked(UserAccount userAccount){
        usersLiked.add(userAccount);
    }

    /**
     * Removes User who liked this message
     * 
     * @param userAccount UserAccount
     */
    public void removeUsersLiked(UserAccount userAccount){
        usersLiked.remove(userAccount);
    }

     /**
     * Adds User who disliked this message
     * 
     * @param userAccount UserAccount
     */
    public void addUsersDisliked(UserAccount userAccount){
        usersDisliked.add(userAccount);
    }

    /**
     * Removes User who disliked this message
     * 
     * @param userAccount UserAccount
     */
    public void removeUsersDisliked(UserAccount userAccount){
        usersDisliked.remove(userAccount);
    }

    /**
     * Get content
     * 
     * @return String representing the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Set content
     * 
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }
}
