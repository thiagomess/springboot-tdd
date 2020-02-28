package com.wallet.security.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


import lombok.Data;

@Data
public class JwtAuthenticationDTO {
	
	@NotNull(message = "Informe um email")
	@NotEmpty(message = "Informe um email")
	private String email;
	@NotNull(message = "Informe uma senha")
	@NotEmpty(message = "Informe uma senha")
	private String password;

}
