package fr.k4cook.controllers;

import static fr.k4cook.utils.ControllerUtils.checkErrors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.k4cook.dtos.auth.JwtAuthenticationResponse;
import fr.k4cook.dtos.auth.UserLoginDto;
import fr.k4cook.dtos.auth.UserRegisterDto;
import fr.k4cook.dtos.user.UserResponseDto;
import fr.k4cook.repositories.UserAccountRepository;
import fr.k4cook.services.UserAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

/**
 * User Account Rest Controller
 */
@RestController
@RequestMapping("/user")
public class UserAccountController {

	/**
	 * User Account Service
	 */
	@Autowired
	private UserAccountService userAccountService;

	/**
	 * User Account Repository
	 */
	@Autowired
	private UserAccountRepository userAccountRepository;

	/**
	 * POST Route to register an user
	 * 
	 * @param userRegisterDto
	 * @return Status OK if register, or status 400 if not valid
	 */
	@Operation(summary = "Register an User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully created", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content) })
	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto userRegisterDto,
			BindingResult bindingResult) {
		checkErrors(bindingResult);

		JwtAuthenticationResponse token = userAccountService.register(userRegisterDto);

		return ResponseEntity.ok(token);
	}

	/**
	 * POST Route to log an user
	 * 
	 * @param userLoginDto UserLoginDto
	 * @return Status OK if register, or status 400 if not valid
	 */
	@Operation(summary = "Log an User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully logged in", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponse.class)) }),
			@ApiResponse(responseCode = "400", description = "Validation failed", content = @Content) })
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) {
		checkErrors(bindingResult);

		JwtAuthenticationResponse token = userAccountService.login(userLoginDto);

		return ResponseEntity.ok(token);
	}

	/**
	 * GET Route to get all users
	 * 
	 * @param userLoginDto UserLoginDto
	 * @return Status OK if register, or status 400 if not valid
	 */
	@Operation(summary = "Get All Users")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully find all", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto[].class)) }),
			@ApiResponse(responseCode = "400", description = "", content = @Content) })
	@GetMapping()
	public ResponseEntity<?> getUsers() {
		return ResponseEntity
				.ok(userAccountRepository.findAll().stream().map(user -> new UserResponseDto(user)).toList());
	}

	/**
	 * GET Route to get all users
	 * 
	 * @param userLoginDto UserLoginDto
	 * @return Status OK if register, or status 400 if not valid
	 */
	@Operation(summary = "Get All Users by containing")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully find all", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto[].class)) }),
			@ApiResponse(responseCode = "400", description = "", content = @Content) })
	@GetMapping("/email/{email}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getUsersByEmail(@PathVariable String email) {
		return ResponseEntity
				.ok(userAccountRepository.findByEmailContaining(email).stream().map(user -> new UserResponseDto(user))
						.toList());
	}

	/**
	 * PATCH Route to suspend an user
	 * 
	 * @param int id
	 * @param int days
	 * @return user updated
	 */
	@Operation(summary = "Suspend an User")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "User successfully suspended", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)) }), })
	@CrossOrigin(origins = "http://localhost:4200")
	@PatchMapping("/{id}/suspend/{days}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> suspendUser(@PathVariable int id, @PathVariable int days) {
		return ResponseEntity.ok(new UserResponseDto(userAccountService.suspend(id, days)));
	}

	/**
	 * PATCH Route to unsuspend an user
	 * 
	 * @param int id
	 * @param int days
	 * @return user updated
	 */
	@Operation(summary = "Unuspend an User")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "User successfully unsuspended", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class)) }), })
	@PatchMapping("/{id}/unsuspend")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> unsuspendUser(@PathVariable int id) {
		return ResponseEntity.ok(new UserResponseDto(userAccountService.unsuspend(id)));
	}

	@GetMapping("/getEmails")
	public ResponseEntity<?> getEmails() {
		return ResponseEntity.ok(userAccountService.findEmails());
	}
}
