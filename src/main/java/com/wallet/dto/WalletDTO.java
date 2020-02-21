package com.wallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class WalletDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	@NotNull(message = "O nome não pode ser nulo")
	@Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres")
	private String name;
	@NotNull(message = "Insira um valor para a carteira")
	private BigDecimal value; 

}
