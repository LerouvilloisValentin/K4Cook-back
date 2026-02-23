package fr.k4cook.K4Cook.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.k4cook.K4Cook.dtos.auth.JwtAuthenticationResponse;
import fr.k4cook.K4Cook.dtos.auth.UserLoginDto;
import fr.k4cook.K4Cook.dtos.auth.UserRegisterDto;
import fr.k4cook.K4Cook.entities.UserAccount;
import fr.k4cook.K4Cook.mappers.UserMapper;
import fr.k4cook.K4Cook.repositories.UserAccountRepository;
import jakarta.annotation.PostConstruct;

/**
 * Service of UserAccount
 */
@Service
public class UserAccountService {
	/**
	 * UserAccount repository
	 */
	@Autowired
	UserAccountRepository userAccountRepository;

	/**
	 * Password encoder
	 */
	@Autowired
	PasswordEncoder passwordEncoder;

	/**
	 * JwtService
	 */
	@Autowired
	private JwtService jwtService;

	/**
	 * AuthenticationManager
	 */
	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Initialize 2 users in database after construct
	 */
	@PostConstruct
	public void init() {
		create(new UserAccount("Doboles", "k4cooks", "admin6@test.com", passwordEncoder.encode("admin"), "ROLE_ADMIN",
				"ROLE_USER"));
		create(new UserAccount("user", "user", "user@test.com", passwordEncoder.encode("user"), "ROLE_USER"));
		create(new UserAccount("user", "user", "user2@test.com", passwordEncoder.encode("user"), "ROLE_USER"));

	}

	/**
	 * Create a User
	 * 
	 * @param user
	 */
	public UserAccount create(UserAccount user) {
		return userAccountRepository.save(user);
	}

	/**
	 * Register an user by a UserRegisterDto
	 * 
	 * @param userRegisterDto Dto
	 * @throws IllegalArgumentException if an user is already using this email
	 */
	public JwtAuthenticationResponse register(UserRegisterDto userRegisterDto) throws IllegalArgumentException {
		if (!userAccountRepository.findAllByEmail(userRegisterDto.getEmail()).isEmpty()) {
			throw new IllegalArgumentException("Email is already used");
		}

		UserAccount userAccount = new UserAccount(
				userRegisterDto.getFirstName(),
				userRegisterDto.getLastName(),
				userRegisterDto.getEmail(),
				passwordEncoder.encode(userRegisterDto.getPassword()),
				"ROLE_USER");

		var user = create(userAccount);

		var jwt = jwtService.generateToken(userAccount.getEmail());
		List<String> roles = userAccount.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();

		return new JwtAuthenticationResponse(jwt, user.getId(), roles);
	}

	/**
	 * Login an user if their information are correct
	 * 
	 * @param userLoginDto UserLoginDto
	 * @return JwtAuthenticationResponse
	 * @throws IllegalArgumentException if information not correct
	 */
	public JwtAuthenticationResponse login(UserLoginDto userLoginDto) throws IllegalArgumentException {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword()));

		var user = userAccountRepository.findByEmail(userLoginDto.getEmail());
		if (user == null) {
			throw new IllegalArgumentException("Invalid username/email or password");
		} else if (user.isSuspended()) {
			throw new AuthenticationServiceException("User suspended");
		}

		String jwt = jwtService.generateToken(UserMapper.toUserDetails(user).getUsername());
		List<String> roles = user.getAuthorities().stream().map(authority -> authority.getAuthority()).toList();

		return new JwtAuthenticationResponse(jwt, user.getId(), roles);
	}

	/**
	 * Suspend a user for X days
	 * 
	 * @param id   User'id
	 * @param days Days of suspension
	 * @throws IllegalArgumentException if user not found
	 */
	public UserAccount suspend(int id, int days) throws IllegalArgumentException {
		UserAccount user = userAccountRepository.findById(id).orElse(null);

		if (user == null) {
			throw new IllegalArgumentException("Invalid username/email or password");
		}

		LocalDateTime suspendedTill = LocalDateTime.now().plusDays(days);
		user.setSuspendedTillDate(suspendedTill);
		userAccountRepository.save(user);

		return user;
	}

	/**
	 * Unsuspend a user
	 * 
	 * @param id User'id
	 * @throws IllegalArgumentException if user not found
	 */
	public UserAccount unsuspend(int id) throws IllegalArgumentException {
		UserAccount user = userAccountRepository.findById(id).orElse(null);

		if (user == null) {
			throw new IllegalArgumentException("Invalid username/email or password");
		}

		LocalDateTime suspendedTill = LocalDateTime.now().minusDays(1);
		user.setSuspendedTillDate(suspendedTill);
		userAccountRepository.save(user);

		return user;
	}

	public List<String> findEmails() {
		
		return userAccountRepository.findAll().stream().map(u -> u.getEmail()).toList();
	}
}
