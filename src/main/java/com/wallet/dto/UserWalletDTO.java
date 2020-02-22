package com.wallet.dto;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UserWalletDTO {
	
	private Long id;
	@NotNull(message = "O id do User não poder ser nulo")
	private Long user;
	@NotNull(message = "O id da Wallet não poder ser nulo")
	private Long wallet;

}
