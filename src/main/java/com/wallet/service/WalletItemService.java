package com.wallet.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;

public interface WalletItemService {

	WalletItem save(WalletItem walletItem);

	Page<WalletItem> findBetweenDates(Long idWallet, Date dateInit, Date dateEnd, PageRequest pg);

	Optional<WalletItem> findById(Long id);

	WalletItem update(WalletItem walletItem);

	void delete(WalletItem walletItem);

	@Cacheable(value = "findByWalletAndType") // configurado no xml
	List<WalletItem> findByWalletIdAndType(Long idWallet, TypeEnum type);

	BigDecimal sumByWalletId(long idWallet);
}
