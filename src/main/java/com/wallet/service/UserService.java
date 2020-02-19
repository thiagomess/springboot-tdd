package com.wallet.service;

import java.util.Optional;

import com.wallet.entity.User;

public interface UserService {

	Optional<User> findByEmail(String string);

	User save(User u);

}
