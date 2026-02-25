package fr.k4cook.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

import fr.k4cook.filters.JwtAuthFilter;
import fr.k4cook.services.UserDetailsServiceImpl;

/**
 * Security Config
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	public static final List<RequestMatcher> ignoredGetPaths = new ArrayList<RequestMatcher>(Arrays.asList(
			new AntPathRequestMatcher("/"),
			new AntPathRequestMatcher("/swagger-ui"),
			new AntPathRequestMatcher("/swagger-ui/**"),
			new AntPathRequestMatcher("/v3/api-docs/**"),
			new AntPathRequestMatcher("/login"),
			new AntPathRequestMatcher("/user/login"),
			new AntPathRequestMatcher("/user/register"),
			new AntPathRequestMatcher("/user/getEmails"),
			new AntPathRequestMatcher("/forums"),
			new AntPathRequestMatcher("/forums/**"),
			new AntPathRequestMatcher("/sections"),
			new AntPathRequestMatcher("/sections/**"),
			new AntPathRequestMatcher("/messages"),
			new AntPathRequestMatcher("/messages/**")));

	public static final List<RequestMatcher> ignoredPostPaths = new ArrayList<RequestMatcher>(Arrays.asList(
			new AntPathRequestMatcher("/user/login"),
			new AntPathRequestMatcher("/user/register"),
			new AntPathRequestMatcher("/user/getEmails")));

	/**
	 * Cors Configuration Source
	 */
	@Autowired
	private CorsConfigurationSource corsConfigurationSource;

	/**
	 * Bean defining JwtAuthFilter
	 * 
	 * @return JwtAuthFilter
	 */
	@Bean
	JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter();
	}

	/**
	 * Bean defining UserDetailsService Implementation
	 * 
	 * @return UserDetailsService
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	/**
	 * Bean defining a password encoder (BCrypt)
	 * 
	 * @return PasswordEncoder
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Bean defining a authentication manager
	 * 
	 * @return AuthenticationManager
	 */
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	/**
	 * Bean defining a authentication provider
	 * 
	 * @return AuthenticationProvider
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();

		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	/**
	 * Bean defining a Security Filter Chain
	 * For now, all routes are permit
	 * 
	 * @param httpSecu HttpSecurity
	 * @return SecurityFilterChain
	 * @throws Exception
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecu) throws Exception {
		httpSecu.csrf(csrf -> csrf.disable());
		httpSecu.cors(cors -> cors.configurationSource(corsConfigurationSource));

		httpSecu.authorizeHttpRequests(request -> request
				.requestMatchers(
						"/",
						"/swagger-ui",
						"/swagger-ui/**",
						"/v3/api-docs/**",
						"/login",
						"/user/login",
						"/user/register",
						"/user/getEmails",
						"/forums",
						"/forums/**",
						"/sections",
						"/sections/**",
						"/messages",
						"/messages/**")
				.permitAll()
				.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class)
				.sessionManagement((session) -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider());

		return httpSecu.build();
	}

	public class TestPassword {
		public static void main(String[] args) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println(encoder.encode("admin123"));
		}
	}
}
