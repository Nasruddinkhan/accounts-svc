package com.sa.tawuniya.assingment.account;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT  )
@ActiveProfiles("test")
class AccountsSvcApplicationTests {

	@LocalServerPort
	private int port;

	@InjectMocks
	AccountsSvcApplication testRestTemplate;

	@Test
	void contextLoads() {
		assertNotNull(testRestTemplate);

	}

}
