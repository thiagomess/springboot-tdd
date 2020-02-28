package com.wallet.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wallet.entity.WalletItem;
import com.wallet.repository.WalletItemRepository;
import com.wallet.service.WalletItemService;
import com.wallet.util.enums.TypeEnum;

@Service
public class WalletItemServiceImpl implements WalletItemService {
	
	private static final Logger log = LoggerFactory.getLogger(WalletItemServiceImpl.class);

//	@Value("${pagination.items_per_page}")
//	private int itemsPerPage;

	@Autowired
	private WalletItemRepository repository;

	@Override
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public WalletItem save(WalletItem walletItem) {
		return repository.save(walletItem);
	}

	@Override
	public Page<WalletItem> findBetweenDates(Long idWallet, Date dateInit, Date dateEnd, PageRequest pg) {

//      poderia receber qual a pagina por parametro e os items ppor pagina, definidos no application.properties
//		PageRequest pg = new PageRequest(page, itemsPerPage);
		return repository.findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(idWallet, dateInit, dateEnd, pg);
	}

	@Override
	public Optional<WalletItem> findById(Long id) {
		return repository.findById(id);
	}

	@Override
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public WalletItem update(WalletItem newObj) {
		return repository.save(newObj);
	}

	@Override
	@CacheEvict(value = "findByWalletAndType", allEntries = true)
	public void delete(WalletItem w) {
		repository.delete(w);
	}

	@Override
	@Cacheable(value = "findByWalletAndType") // configurado no xml
	public List<WalletItem> findByWalletIdAndType(Long idWallet, TypeEnum type) {
		log.info("Indo no banco pesquisar por findByWalletAndType ");
		return repository.findByWalletIdAndType(idWallet, type);
	}

	@Override
	public BigDecimal sumByWalletId(long idWallet) {
		return repository.sumByWalletId(idWallet);
	}

}
