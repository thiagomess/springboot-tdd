package com.wallet.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.wallet.entity.Wallet;

import lombok.Data;

@Data
public class WalletItemDTO {

	private Long id;
	@NotNull(message = "Insira o ID da carteira")
	private Wallet wallet;
	@NotNull(message = "Informe uma data")
	private Date date;
	@NotNull(message = "Informe um tipo")
	@Pattern(regexp = "^(ENTRADA|SAIDA)$", message = "Para o tipo somente são aceitos os valores ENTRADA ou SAIDA")
	private String type;
	@NotNull(message = "Informe uma descricao")
	@Length(min = 5, message = "A descrição deve ter 5 caracteres")
	private String description;
	@NotNull(message = "Informe um valor")
	private BigDecimal value;
}
