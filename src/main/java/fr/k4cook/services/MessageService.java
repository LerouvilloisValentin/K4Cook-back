package fr.k4cook.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import fr.k4cook.dtos.message.MessageLikeResponseDto;
import fr.k4cook.dtos.message.MessagePostDto;
import fr.k4cook.dtos.message.MessagePutDto;
import fr.k4cook.entities.Message;
import fr.k4cook.entities.UserAccount;
import fr.k4cook.repositories.MessageRepository;
import fr.k4cook.utils.ControllerUtils;

/**
 * Message Service
 */
@Service
public class MessageService {

    /**
     * Message Repository
     */
    @Autowired
    private MessageRepository messageRepository;

    /**
     * Autowired ControllerUtils
     */
    @Autowired
    private ControllerUtils controllerUtils;

    /**
     * Create a Message and return it
     * 
     * @param messagePostDto Dto
     * @param userAccount    Authenticated User
     * @return Message newly savec
     */
    public Message create(MessagePostDto messagePostDto, UserAccount userAccount) throws IllegalArgumentException {

        Message parentMessage = messageRepository.findById(messagePostDto.getParentMessageId()).orElse(null);

        if (parentMessage == null && messagePostDto.getParentMessageId() != -1) {
            throw new NoSuchElementException("Parent message doesn't exist");
        }

        Message message = new Message();

        message.setContent(messagePostDto.getContent());
        message.setParentMessage(parentMessage);
        message.setUser(userAccount);
        message.setDate(LocalDateTime.now());

        return messageRepository.save(message);
    }

    /**
     * Get message by id
     * 
     * @param id int
     * @return Message
     * @throws NoSuchElementException if Message is not find
     */
    public Message getById(int id) throws NoSuchElementException {
        Message message = messageRepository.findById(id).orElse(null);

        if (message == null) {
            throw new NoSuchElementException("Could not find message");
        }

        return message;
    }

    /**
     * Get all
     * 
     * @return List of Message
     */
    public List<Message> getAll() {
        return messageRepository.findAll();
    }

    /**
     * Update by id
     * 
     * @param id            int
     * @param messagePutDto Dto
     * @return Message updated
     */
    public Message updateById(int id, MessagePutDto messagePutDto, UserDetails userDetails) {
        Message message = getById(id);
        isOwnerUserOrAdmin(message, userDetails);

        message.setContent(messagePutDto.getContent());

        messageRepository.save(message);
        return message;
    }

    /**
     * Delete by id
     * 
     * @param id          int
     * @param userDetails UserDetails
     */
    public void deleteById(int id, UserDetails userDetails) {
        Message message = getById(id);

        isOwnerUserOrAdmin(message, userDetails);
        messageRepository.delete(message);
    }

    /**
     * Like a message and attach it to user
     * 
     * @param id          int
     * @param userDetails UserDetails
     */
    public MessageLikeResponseDto like(int id, UserDetails userDetails) {
        Message message = getById(id);
        UserAccount user = controllerUtils.getUserAccount(userDetails);
        boolean isLiked = false;

        if (message.getUsersDisliked().contains(user)) {
            message.removeUsersDisliked(user);
        }

        if (message.getUsersLiked().contains(user)) {
            message.removeUsersLiked(user);
            isLiked = false;
        } else {
            message.addUsersLiked(user);
            isLiked = true;
        }

        messageRepository.save(message);

        return new MessageLikeResponseDto(isLiked, false);
    }

    /**
     * Dislike a message and attach it to user
     * 
     * @param id          int
     * @param userDetails UserDetails
     */
    public MessageLikeResponseDto dislike(int id, UserDetails userDetails) {
        Message message = getById(id);
        UserAccount user = controllerUtils.getUserAccount(userDetails);
        boolean isDisliked = false;

        if (message.getUsersLiked().contains(user)) {
            message.removeUsersLiked(user);
        }

        if (message.getUsersDisliked().contains(user)) {
            message.removeUsersDisliked(user);
            isDisliked = false;
        } else {
            message.addUsersDisliked(user);
            isDisliked = true;
        }

        messageRepository.save(message);

        return new MessageLikeResponseDto(false, isDisliked);
    }

    /**
     * Check if the user owns the section or if he's admin
     * 
     * @param id          int
     * @param userDetails UserDetails
     * @throws AuthenticationServiceException if user isn't onwer or isn't admin
     */
    private void isOwnerUserOrAdmin(Message message, UserDetails userDetails) throws AuthenticationServiceException {
        UserAccount userAccount = controllerUtils.getUserAccount(userDetails);

        if (!message.getUser().equals(userAccount)
                && !userAccount.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
            throw new AuthenticationServiceException("Not Authorized");
        }
    }
}
