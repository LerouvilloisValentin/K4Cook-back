package fr.k4cook.dtos.message;

import java.time.LocalDateTime;
import java.util.List;

import fr.k4cook.dtos.user.UserResponseDto;
import fr.k4cook.entities.Message;
import fr.k4cook.entities.UserAccount;

/**
 * Data Transfer Object (DTO) for transferring message data between processes.
 * <p>
 * This DTO encapsulates message details such as ID, content, likes, dislikes,
 * parent message ID,
 * associated user details, and the date the message was posted or last
 * modified. It is primarily used
 * for sending message data from the server to the client in a structured
 * format.
 */
public class MessageResponseDto {

    /**
     * Unique identifier for the message.
     */
    private int id;

    /**
     * Content of the message.
     */
    private String content;

    /**
     * Date and time when the message was posted or last modified.
     */
    private LocalDateTime date;

    /**
     * Number of likes received by the message.
     */
    private int like;

    /**
     * User ids that has liked
     */
    private List<Integer> likeUserIds;

    /**
     * Number of dislikes received by the message.
     */
    private int dislike;

    /**
     * User ids that has disliked
     */
    private List<Integer> dislikeUserIds;

    /**
     * Identifier of the parent message, if any.
     */
    private int parentMessageId;

    /**
     * User who posted the message.
     */
    private UserResponseDto user;

    /**
     * Constructs a new {@code MessageResponseDto} object using details from a
     * {@link Message}.
     *
     * @param message the {@code Message} object containing the message's details
     */
    public MessageResponseDto(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.date = message.getDate();
        this.like = message.getUsersLiked().size();
        this.likeUserIds = message.getUsersLiked().stream().map(UserAccount::getId).toList();
        this.dislike = message.getUsersDisliked().size();
        this.dislikeUserIds = message.getUsersDisliked().stream().map(UserAccount::getId).toList();
        this.user = new UserResponseDto(message.getUser());

        if (message.getParentMessage() != null) {
            this.parentMessageId = message.getParentMessage().getId();
        }
    }

    /**
     * Retrieves the unique identifier of the message.
     *
     * @return the message's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the message.
     *
     * @param id the new message ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the content of the message.
     *
     * @return the message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the message.
     *
     * @param content the new message content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Retrieves the date and time when the message was posted or last modified.
     *
     * @return the {@code LocalDateTime} representing the date and time of the
     *         message
     */
    public LocalDateTime getDate() {
        return date;
    }

    /**
     * Sets the date and time when the message was posted or last modified.
     *
     * @param date the new date and time for the message
     */
    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    /**
     * Retrieves the number of likes received by the message.
     *
     * @return the number of likes
     */
    public int getLike() {
        return like;
    }

    /**
     * Sets the number of likes received by the message.
     *
     * @param like the new number of likes
     */
    public void setLike(int like) {
        this.like = like;
    }

    /**
     * Retrieves the number of dislikes received by the message.
     *
     * @return the number of dislikes
     */
    public int getDislike() {
        return dislike;
    }

    /**
     * Sets the number of dislikes received by the message.
     *
     * @param dislike the new number of dislikes
     */
    public void setDislike(int dislike) {
        this.dislike = dislike;
    }

    /**
     * Retrieves the identifier of the parent message, if any.
     *
     * @return the parent message ID
     */
    public int getParentMessageId() {
        return parentMessageId;
    }

    /**
     * Sets the identifier of the parent message.
     *
     * @param parentMessageId the new parent message ID
     */
    public void setParentMessageId(int parentMessageId) {
        this.parentMessageId = parentMessageId;
    }

    /**
     * Retrieves the details of the user who posted the message.
     *
     * @return the {@code UserResponseDto} associated with the message
     */
    public UserResponseDto getUser() {
        return user;
    }

    /**
     * Sets the details of the user who posted the message.
     *
     * @param user the {@code UserResponseDto} object representing the user
     */
    public void setUser(UserResponseDto user) {
        this.user = user;
    }

    /**
     * Get user's ids that has liked this message
     * 
     * @return List of ids
     */
    public List<Integer> getLikeUserIds() {
        return likeUserIds;
    }

    /**
     * Set user's ids that has liked this message
     * 
     * @param likeUserIds
     */
    public void setLikeUserIds(List<Integer> likeUserIds) {
        this.likeUserIds = likeUserIds;
    }

    /**
     * Get user's ids that has disliked this message
     * 
     * @return List of ids
     */
    public List<Integer> getDislikeUserIds() {
        return dislikeUserIds;
    }

    /**
     * Set user's ids that has disliked this message
     * 
     * @param dislikeUserIds
     */
    public void setDislikeUserIds(List<Integer> dislikeUserIds) {
        this.dislikeUserIds = dislikeUserIds;
    }
}
