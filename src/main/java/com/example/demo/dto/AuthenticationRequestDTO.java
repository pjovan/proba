package com.example.demo.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class AuthenticationRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	@NotBlank
	@Setter(AccessLevel.NONE)
	private String username;
	@NotNull
	@NotBlank
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*])(?=\\S+$).{8,255}$")
	private String password;
//	private String passwordResetToken;

	public void setUsername(String username) {
		this.username = username.toLowerCase();
	}

	public AuthenticationRequestDTO(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}

}
