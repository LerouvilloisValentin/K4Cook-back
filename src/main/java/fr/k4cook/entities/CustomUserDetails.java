package fr.k4cook.entities;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Custom implementation of UserDetails for Spring Security.
 */
public class CustomUserDetails implements UserDetails {

    /** The username of the user */
    private String username;

    /** The password of the user */
    private String password;

    /** The authorities granted to the user */
    private final List<String> roles;
    private boolean isNonLocked = true;

    /**
     * Constructs a new CustomUserDetails object based on the given UserAccount.
     *
     * @param byUsername the UserAccount to copy data from
     */
    public CustomUserDetails(UserAccount byUsername) {
        this.username = byUsername.getEmail();
        this.password = byUsername.getPassword();
        this.roles = byUsername.getAuthorities();
        this.isNonLocked = !LocalDateTime.now().isBefore(byUsername.getSuspendedTillDate());
    }

    /**
     * Returns the authorities granted to the user.
     * 
     * @return the authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    /**
     * Returns the password of the user.
     * 
     * @return the password
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username of the user.
     * 
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Indicates whether the user's account has expired. This implementation
     * always returns {@code true}.
     * 
     * @return {@code true} if the account is non-expired, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is locked or unlocked. This implementation
     * always returns {@code true}.
     * 
     * @return {@code true} if the account is non-locked, {@code false} otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return isNonLocked;
    }

    /**
     * Indicates whether the user's credentials (password) has expired. This
     * implementation
     * always returns {@code true}.
     * 
     * @return {@code true} if the credentials are non-expired, {@code false}
     *         otherwise
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled or disabled. This implementation
     * always returns {@code true}.
     * 
     * @return {@code true} if the user is enabled, {@code false} otherwise
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
