package fr.k4cook.K4Cook.controllers;

import static fr.k4cook.K4Cook.utils.ControllerUtils.checkErrors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.k4cook.K4Cook.dtos.message.MessagePostDto;
import fr.k4cook.K4Cook.dtos.message.MessagePutDto;
import fr.k4cook.K4Cook.dtos.message.MessageResponseDto;
import fr.k4cook.K4Cook.entities.Message;
import fr.k4cook.K4Cook.services.MessageService;
import fr.k4cook.K4Cook.utils.ControllerUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * Message Controller
 * Can be requested at /messages
 */
@RestController
@RequestMapping("/messages")
public class MessageController {

    /**
     * Autowired ControllerUtils
     */
    @Autowired
    private ControllerUtils controllerUtils;

    /**
     * Autowired MessageService
     */
    @Autowired
    private MessageService messageService;

    /**
     * Post a Message using the Authentication
     * 
     * @param messagePostDto Dto expected
     * @param userDetails    Authenticated user
     * @param bindingResult  Error from validation
     * @return 200 with JSON Message body, 404 if validation failed
     */
    @Operation(summary = "Create a Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content) })
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> post(@Valid @RequestBody MessagePostDto messagePostDto,
            @AuthenticationPrincipal UserDetails userDetails, BindingResult bindingResult) {
        checkErrors(bindingResult);

        Message message = messageService.create(messagePostDto, controllerUtils.getUserAccount(userDetails));

        return ResponseEntity.ok(new MessageResponseDto(message));
    }

    /**
     * Get all sections
     * 
     * @return 200 with JSON List of Message body
     */
    @Operation(summary = "Get all sections")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto[].class)) }) })
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(messageService.getAll());
    }

    /**
     * Get a Message by id
     * 
     * @param id Path variable expected to be an id
     * @return 200 with JSON Message body, 404 if not found
     */
    @Operation(summary = "Get a Message by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get the section correspondig to the id", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class)) }),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content) })
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        return ResponseEntity.ok(new MessageResponseDto(messageService.getById(id)));
    }

    /**
     * Update a Message by id
     * 
     * @param id            Path variable expected to be an id
     * @param messagePutDto Dto expected
     * @param bindingResult Error from validation
     * @param userDetails   Authenticated user
     * @return 200 with JSON Message body, 400 if validation failed
     */
    @Operation(summary = "Update a Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully created", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content) })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateById(@PathVariable int id, @Valid @RequestBody MessagePutDto messagePutDto,
            BindingResult bindingResult, @AuthenticationPrincipal UserDetails userDetails) {
        checkErrors(bindingResult);

        Message message = messageService.updateById(id, messagePutDto, userDetails);

        return ResponseEntity.ok(new MessageResponseDto(message));
    }

    /**
     * Delete a Message by id
     * The user must be an administrator
     * 
     * @param id          Path variable expected to be an id
     * @param userDetails Authenticated user
     * @return 200, 404 if not found
     */
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Delete a Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content) })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity deleteById(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        messageService.deleteById(id, userDetails);

        return ResponseEntity.ok().build();
    }

    /**
     * Like a Message by id
     * 
     * @param id          int
     * @param userDetails UserDetails
     * @return 200
     */
    @Operation(summary = "Like a Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully liked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content) })
    @PostMapping("/{id}/like")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> likeById(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(messageService.like(id, userDetails));
    }

    /**
     * Dislike a Message by id
     * 
     * @param id          int
     * @param userDetails UserDetails
     * @return 200
     */
    @SuppressWarnings("rawtypes")
    @Operation(summary = "Dislike a Message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message successfully liked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Message not found", content = @Content) })
    @PostMapping("/{id}/dislike")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity dislikeById(@PathVariable int id, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(messageService.dislike(id, userDetails));
    }
}
