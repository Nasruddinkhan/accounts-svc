package com.sa.tawuniya.assingment.account;

import com.sa.tawuniya.assingment.account.repository.AccountRepository;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@SecurityScheme(
		name = "account-api",
		type = SecuritySchemeType.APIKEY,
		bearerFormat = "JWT",
		scheme = "Bearer",
		paramName = "Authorization",
		in = SecuritySchemeIn.HEADER
)
public class AccountsSvcApplication{
	private final AccountRepository accountRepository;
	public static void main(String[] args) {
		SpringApplication.run(AccountsSvcApplication.class, args);
	}


}
