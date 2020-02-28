package com.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wallet.entity.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long>{

	Optional<UserWallet> findByUserIdAndWalletId(Long user, Long wallet);
}
