package fr.k4cook.K4Cook.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.k4cook.K4Cook.entities.CustomUserDetails;
import fr.k4cook.K4Cook.repositories.UserAccountRepository;

/**
 * Implementation of the UserDetailsService interface.
 * 
 * This service is used by Spring Security to retrieve user details for
 * authentication.
 * It interacts with the UserAccountRepository to fetch user details based on
 * the username.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    /**
     * Repository for accessing UserAccount data.
     */
    @Autowired
    private UserAccountRepository userAccountRepository;

    /**
     * Logger for logging debug and error messages.
     */
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    /**
     * Loads user details by username.
     * 
     * This method fetches the UserAccount entity from the repository using the
     * provided username.
     * If the user is not found, it throws UsernameNotFoundException.
     * 
     * @param username the username of the user to be loaded
     * @return UserDetails containing the user's information
     * @throws UsernameNotFoundException if no user is found with the given username
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Entering loadUserByUsername method...");
        var user = userAccountRepository.findByEmail(username);
        if (user == null) {
            logger.error("Username not found: " + username);
            throw new UsernameNotFoundException("User not found.");
        }
        logger.info("User authenticated successfully.");
        return new CustomUserDetails(user);
    }
}
