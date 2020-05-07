package com.wallet.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.wallet.entity.User;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

	private static final String EMAIL = "email@teste.com";

	@Autowired
	UserRepository repository;

	@BeforeEach
	public void setup() {
		User u = new User();
		u.setName("set up user");
		u.setPassword("senha123");
		u.setEmail(EMAIL);
		repository.save(u);
	}

	@AfterEach
	public void tearDown() {
		repository.deleteAll();
	}

	@Test
	public void testSave() {
		User u = new User();
		u.setName("Teste");
		u.setPassword("123");
		u.setEmail("teste@teste.com");

		User response = repository.save(u);

		assertNotNull(response);
	}

	@Test
	public void testFindByEmail() {
		Optional<User> response = repository.findByEmailEquals(EMAIL);

		assertTrue(response.isPresent());
		assertEquals(EMAIL, response.get().getEmail());
	}

}
