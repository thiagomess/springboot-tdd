package com.wallet.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wallet.entity.WalletItem;
import com.wallet.util.enums.TypeEnum;

@Repository
public interface WalletItemRepository extends JpaRepository<WalletItem, Long>{

	//Usado docs Como Wallet é chave estrangeira, tem que passar o campo referente a ela ficando WalletId
	//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
	Page<WalletItem> findAllByWalletIdAndDateGreaterThanEqualAndDateLessThanEqual(Long wallet, Date id,
			Date end, Pageable pg);

	List<WalletItem> findByWalletIdAndType(Long wallet, TypeEnum type);

	//Existe outros parametros como paginação no @Query
	@Query(value = "SELECT sum(value) FROM WalletItem wi WHERE wi.wallet.id = :wallet")
	BigDecimal sumByWalletId(@Param("wallet")  Long wallet);

}
