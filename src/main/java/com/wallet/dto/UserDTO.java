package com.wallet.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data 
@JsonInclude(Include.NON_NULL) // nao devolve campos nulos para a API
public class UserDTO {

	private Long id;
	@Length(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres")
	private String name;
	@Email(message = "Email inválido")
	private String email;
	@NotNull
	@Length(min = 5, message = "A senha deve conter no mínimo 6 caracteres")
	private String password;
	@NotNull(message = "Informe a Role de acesso")
	@Pattern(regexp = "^(ROLE_ADMIN|ROLE_USER)$", message = "Para a role de acesso somente são aceitos os valores ROLE_ADMIN e ROLE_USER")
	private String role;

}
