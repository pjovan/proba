package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.filter.JwtFilter;
import com.example.demo.util.JwtUtil;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
	@Autowired
	private UserDetailsService myUserDetailsService;

	@Autowired
	private JwtFilter jwtTokenFilter;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

//	@Bean
//	protected CorsConfigurationSource corsConfigurationSource() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//		return source;
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
	}

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		// Enable CORS and disable CSRF
//		http = http.csrf().disable();
//
//		// Set session management to stateless
//		http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
//
//		// Set unauthorized requests exception handler
//		http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
//		}).and();
//
//		// Set permissions on endpoints
//		http.authorizeRequests()
//				// Our public endpoints
//				.antMatchers("/authenticate", "/forgot-password", "/reset-password").permitAll().antMatchers()
//				.permitAll()
//
//				// Our private endpoints
//				.anyRequest().authenticated().and().formLogin().permitAll().loginProcessingUrl("/login")
//				.successHandler((req, res, auth) -> res.setStatus(HttpStatus.NO_CONTENT.value()))
//				.failureHandler(new SimpleUrlAuthenticationFailureHandler()).and().exceptionHandling()
//				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).and().sessionManagement()
//				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//
//		// Add JWT token filter
//		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
//	}

//	// Used by spring security if CORS is enabled.
//	@Bean
//	UrlBasedCorsConfigurationSource corsConfigurationSource() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOriginPattern("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//
//		source.registerCorsConfiguration("/**", config);
//		return source;
//	}

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/api/**").allowedOrigins("http://localhost:3000")
//				.allowedMethods("PUT", "DELETE", "POST", "GET").allowedHeaders("Authorization");
//
//	}
}
