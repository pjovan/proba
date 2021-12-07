package com.example.demo.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private final String jwt;
	private final int type;

}
