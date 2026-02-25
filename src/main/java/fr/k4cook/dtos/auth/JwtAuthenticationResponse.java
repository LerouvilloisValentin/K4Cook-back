package fr.k4cook.dtos.auth;

import java.util.List;

/**
 * Dto usually used to send a Response containing a JWT
 */
public class JwtAuthenticationResponse {
    /**
     * Token
     */
    private String token;

    /**
     * User"s id
     */
    private int id;

    /**
     * User role
     */
    private List<String> role;

    /**
     * Empty constructor
     */
    public JwtAuthenticationResponse() {
        super();
    }

    /**
     * Token constructor
     * 
     * @param token String
     */
    public JwtAuthenticationResponse(String token, int id, List<String> role) {
        super();
        this.token = token;
        this.id = id;
        this.role = role;
    }

    /**
     * Get token
     * 
     * @return String representing a token
     */
    public String getToken() {
        return token;
    }

    /**
     * Get role
     * 
     * @return String representing an user role
     */
    public List<String> getRole() {
        return role;
    }

    /**
     * Set token
     * 
     * @param token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Set role
     * 
     * @param role
     */
    public void setRole(List<String> role) {
        this.role = role;
    }

    /**
     * Get id
     * 
     * @return int id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     * 
     * @param id int id
     */
    public void setId(int id) {
        this.id = id;
    }
}
