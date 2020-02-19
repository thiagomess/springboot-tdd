package com.wallet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloWord {

	@Test
	public void testHelloWord() {
		assertEquals(1,1);
	}
}
