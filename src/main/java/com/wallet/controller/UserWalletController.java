package com.wallet.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.UserWalletDTO;
import com.wallet.entity.User;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;

@RestController
@RequestMapping("/user-wallet")
public class UserWalletController {

	@Autowired
	private UserWalletService service;

	@PostMapping
	public ResponseEntity<Response<UserWalletDTO>> save(@Valid @RequestBody UserWalletDTO dto, BindingResult result) {

		Response<UserWalletDTO> response = new Response<>();

		if (result.hasErrors()) {
			result.getAllErrors().forEach(x -> response.getErrors().add(x.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		UserWallet entity = service.save(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(entity));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}

	private UserWalletDTO convertEntityToDto(UserWallet entity) {
		UserWalletDTO uw = new UserWalletDTO();
		uw.setId(entity.getId());
		uw.setUser(entity.getUser().getId());
		uw.setWallet(entity.getWallet().getId());
		return uw;
	}

	private UserWallet convertDtoToEntity(UserWalletDTO dto) {
		User u = new User();
		u.setId(dto.getUser());
		Wallet w = new Wallet();
		w.setId(dto.getWallet());

		UserWallet uw = new UserWallet();
		uw.setUser(u);
		uw.setWallet(w);
		return uw;
	}

}
