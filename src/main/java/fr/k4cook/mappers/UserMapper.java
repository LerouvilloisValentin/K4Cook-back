package fr.k4cook.mappers;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import fr.k4cook.entities.UserAccount;

/**
 * Utility class for mapping {@link UserAccount} entities to {@link UserDetails}
 * objects.
 */
public class UserMapper {

    /**
     * Converts a {@link UserAccount} entity to a {@link UserDetails} object.
     * 
     * @param userAccount the {@link UserAccount} entity to be converted
     * @return a {@link UserDetails} object containing the details of the provided
     *         {@link UserAccount}
     */
    public static UserDetails toUserDetails(UserAccount userAccount) {
        var authorities = userAccount.getAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return User.builder()
                .username(userAccount.getEmail())
                .password(userAccount.getPassword())
                .authorities(authorities)
                .build();
    }
}
