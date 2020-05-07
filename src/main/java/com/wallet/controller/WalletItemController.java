package com.wallet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.dto.WalletItemDTO;
import com.wallet.entity.UserWallet;
import com.wallet.entity.Wallet;
import com.wallet.entity.WalletItem;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;
import com.wallet.service.WalletItemService;
import com.wallet.util.Util;
import com.wallet.util.enums.TypeEnum;

@RestController
@RequestMapping("/wallet-item")
public class WalletItemController {

	@Autowired
	private WalletItemService service;
	
	@Autowired UserWalletService userWalletService;
	
	private static final Logger log = LoggerFactory.getLogger(WalletController.class);

	@PostMapping
	public ResponseEntity<Response<WalletItemDTO>> save(@Valid @RequestBody WalletItemDTO dto, BindingResult result) {
		Response<WalletItemDTO> response = new Response<>();
		if (result.hasErrors()) {
			result.getAllErrors().forEach(x -> response.getErrors().add(x.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		WalletItem entity = service.save(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(entity));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping(value = "/{idWallet}")
	public ResponseEntity<Response<Page<WalletItemDTO>>> findBetweenDates(
			@RequestParam(name = "page", defaultValue = "0") Integer page,
			@RequestParam(name = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
			@RequestParam(value = "endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate,
			@PathVariable Long idWallet) {
		Response<Page<WalletItemDTO>> response = new Response<>();
		
		Optional<UserWallet> uw = userWalletService.findByUsersIdAndWalletId(Util.getAuthenticatedUserId(), idWallet);

		if (uw.isEmpty()) {
			response.getErrors().add("Você não tem acesso a essa carteira");
			return ResponseEntity.badRequest().body(response);
		}
		PageRequest pg = PageRequest.of(page, linesPerPage);
		Page<WalletItem> pageWalletItem = service.findBetweenDates(idWallet, startDate, endDate, pg);
		Page<WalletItemDTO> pageWalletItemDto = pageWalletItem.map(obj -> this.convertEntityToDto(obj));
		response.setData(pageWalletItemDto);
		return ResponseEntity.status(HttpStatus.OK).body(response);

	}

	@GetMapping(value = "/type/{idWallet}")
	public ResponseEntity<Response<List<WalletItemDTO>>> findByWalletIdAndType(@PathVariable Long idWallet,
			@RequestParam("type") String type) {
		log.info("Buscando por carteira {} e tipo {}", idWallet, type);
		
		Response<List<WalletItemDTO>> response = new Response<>();
		
		Optional<UserWallet> uw = userWalletService.findByUsersIdAndWalletId(Util.getAuthenticatedUserId(), idWallet);

		if (uw.isEmpty()) {
			response.getErrors().add("Você não tem acesso a essa carteira");
			return ResponseEntity.badRequest().body(response);
		}
		List<WalletItemDTO> dto = new ArrayList<>();
		List<WalletItem> list = service.findByWalletIdAndType(idWallet, TypeEnum.getEnum(type));

		list.forEach(x -> dto.add(this.convertEntityToDto(x)));
		response.setData(dto);

		return ResponseEntity.ok().body(response);
	}

	@GetMapping(value = "/total/{idWallet}")
	public ResponseEntity<Response<BigDecimal>> sumByWalletId(@PathVariable Long idWallet) {
		Response<BigDecimal> response = new Response<>();

		BigDecimal value = service.sumByWalletId(idWallet);
		response.setData(value == null ? BigDecimal.ZERO : value);

		return ResponseEntity.ok().body(response);
	}

	@PutMapping
	public ResponseEntity<Response<WalletItemDTO>> update(@Valid @RequestBody WalletItemDTO dto, BindingResult result) {
		Response<WalletItemDTO> response = new Response<>();
		Optional<WalletItem> wi = service.findById(dto.getId());

		if (wi.isEmpty()) {
			result.addError(new ObjectError("WalletItem", "WalletItem não encontrado"));
		} else {
			if (wi.get().getWallet().getId().compareTo(dto.getWallet()) != 0) {
				result.addError(new ObjectError("WalletItemChanged", "Você não pode alterar a carteira"));
			}
		}
		if (result.hasErrors()) {
			result.getAllErrors().forEach(x -> response.getErrors().add(x.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}

		WalletItem entity = service.update(this.convertDtoToEntity(dto));

		response.setData(this.convertEntityToDto(entity));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{walletItemId}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<Response<String>> delete(@PathVariable Long walletItemId) {
		Response<String> response = new Response<>();

		Optional<WalletItem> wi = service.findById(walletItemId);

		if (wi.isEmpty()) {
			response.getErrors().add("WalletItem de id " + walletItemId + " não encontrada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		service.delete(wi.get());
		response.setData("WalletItem de id " + walletItemId + " apagada com sucesso");
		return ResponseEntity.ok().body(response);
	}

	private WalletItemDTO convertEntityToDto(WalletItem entity) {
		WalletItemDTO dto = new WalletItemDTO();
		dto.setDate(entity.getDate());
		dto.setDescription(entity.getDescription());
		dto.setId(entity.getId());
		dto.setType(entity.getType().getValue());
		dto.setValue(entity.getValue());
		dto.setWallet(entity.getWallet().getId());
		return dto;
	}

	private WalletItem convertDtoToEntity(WalletItemDTO dto) {
		Wallet w = new Wallet();
		w.setId(dto.getWallet());

		return new WalletItem(dto.getId(), w, dto.getDate(), TypeEnum.getEnum(dto.getType()), dto.getDescription(),
				dto.getValue());
	}

}
