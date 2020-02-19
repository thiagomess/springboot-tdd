package com.wallet.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.dto.UserDTO;
import com.wallet.entity.User;
import com.wallet.service.UserService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc // Ao injetar a classe mock mvc é necessario esta anotação
public class UserControllerTest {

	private static final String NAME = "user Test";

	private static final String PASSWORD = "123456";

	private static final String EMAIL = "email@teste.com.br";

	private static final String URL = "/users";

	@MockBean
	UserService service;

	@Autowired
	MockMvc mvc;

	@Test
	public void testSave() throws Exception {
		
		BDDMockito.given(service.save(Mockito.any(User.class))).willReturn(getMockUser());
		
		mvc.perform(MockMvcRequestBuilders.post(URL)
				.content(getJsonPayload()) //conteudo q a api recebe
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isCreated());
	}

	public User getMockUser() {
		User u = new User();
		u.setName(NAME);
		u.setPassword(PASSWORD);
		u.setEmail(EMAIL);
		return u;
	}

	//Monta o payload da requisicao
	public String getJsonPayload() throws JsonProcessingException {
		UserDTO dto = new UserDTO();
		dto.setName(NAME);
		dto.setPassword(PASSWORD);
		dto.setEmail(EMAIL);

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(dto);
	}

}
