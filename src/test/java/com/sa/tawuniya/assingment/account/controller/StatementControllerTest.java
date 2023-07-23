package com.sa.tawuniya.assingment.account.controller;

import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.dto.error.AccountErrorResponse;
import com.sa.tawuniya.assingment.account.model.entity.Account;
import com.sa.tawuniya.assingment.account.model.entity.Statement;
import com.sa.tawuniya.assingment.account.repository.AccountRepository;
import com.sa.tawuniya.assingment.account.repository.StatementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class StatementControllerTest {
    @LocalServerPort
    private int port;
    final TestRestTemplate restTemplate;

    final AccountRepository accountRepository;

    final StatementRepository statementRepository;
    @Autowired
    StatementControllerTest(TestRestTemplate restTemplate, AccountRepository accountRepository,
                            StatementRepository statementRepository) {

        this.restTemplate = restTemplate;
        this.accountRepository = accountRepository;
        this.statementRepository = statementRepository;
    }


    HttpHeaders headers;

    AccountDto accountDto;
    @BeforeEach
    void setUp() {

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
       var acc = accountRepository.save(new Account(null, "saving", "123456", null));

        statementRepository.saveAll(List.of(
                new Statement(null,acc, "14.10.2012", "196.8"),
                new Statement(null, acc, "01.07.2022", "869.97")
        ));
        var accessToken ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJzdWIiOiJhZG1pbiIsImlhdCI6MTY5MDA2MzA5NSwiZXhwIjoyMDA1NjgyMjk1fQ.JCPDXpy1N5vwYuyBVHugM6dV76GF5fY1JD1wJhwxdt4";
        headers.set("Authorization", "Bearer " + accessToken);

    }


    @Test
    void getStatements(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountDto> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountDto.class);

        assertEquals(200, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("saving", res.getAccountType());
        assertNotNull(res.getAccountNumber());
        assertNotNull(res.getStatements());

    }

    @Test
    void getStatementsByDateRange(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromDate", LocalDate.parse("2010-10-01"))
                .queryParam("toDate",  LocalDate.parse("2023-10-01"))
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountDto> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountDto.class);

        assertEquals(200, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("saving", res.getAccountType());
        assertNotNull(res.getAccountNumber());
        assertNotNull(res.getStatements());

    }
    @Test
    void getStatementsByALL(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromDate", LocalDate.parse("2010-10-01"))
                .queryParam("toDate",  LocalDate.parse("2023-10-01"))
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountDto> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountDto.class);

        assertEquals(200, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("saving", res.getAccountType());
        assertNotNull(res.getAccountNumber());
        assertNotNull(res.getStatements());

    }
    @Test
    void getStatementsByWithoutToDate(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromDate", LocalDate.parse("2010-10-01"))
                .queryParam("fromAmount", 500)
                .queryParam("toAmount", 1000)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }
    @Test
    void getStatementsByWithoutFromDate(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("toDate", LocalDate.parse("2010-10-01"))
                .queryParam("fromAmount", 500)
                .queryParam("toAmount", 1000)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }
    @Test
    void getStatementsByInvalidAmtRange(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromAmount", 1000)
                .queryParam("toAmount", 20)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }

    @Test
    void getStatementsByInvalidDateRange(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromDate", LocalDate.parse("2023-10-01"))
                .queryParam("toDate",  LocalDate.parse("2010-10-01"))
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }

    @Test
    void getStatementsByInvalidDateFormat(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromDate", "10-03-2022")
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }

    @Test
    void getStatementsByInvalidAmount(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("toAmount",  100.00)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }

    @Test
    void getStatementsByInvalidFromAmount(){
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("fromAmount",  100.00)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(400, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("400 BAD_REQUEST", res.code());
        assertEquals("Invalid Payload", res.message());

    }
    @Test
    void getThreeMonthStatements(){
        var accessToken ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX3VzZXIifV0sInN1YiI6InVzZXIiLCJpYXQiOjE2OTAwNjQwNjEsImV4cCI6MjAwNTY4MzI2MX0.BnfED16lldGsvDd-i0Kw7N5y2BNzffBZD3SZWx4ExLM";
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountDto> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountDto.class);

        assertEquals(200, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("saving", res.getAccountType());
        assertNotNull(res.getAccountNumber());
        assertNotNull(res.getStatements());

    }

    @Test
    void getStatementsByInvalidPermission(){
        var accessToken ="eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJhdXRob3JpdHkiOiJST0xFX3VzZXIifV0sInN1YiI6InVzZXIiLCJpYXQiOjE2OTAwNjQwNjEsImV4cCI6MjAwNTY4MzI2MX0.BnfED16lldGsvDd-i0Kw7N5y2BNzffBZD3SZWx4ExLM";
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        final URI targetUrl = fromUriString("http://localhost:" + port + "/accounts-svc/api/v1/statements")
                .queryParam("accountId", 1L)
                .queryParam("toAmount",  100.00)

                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        ResponseEntity<AccountErrorResponse> response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                AccountErrorResponse.class);

        assertEquals(403, response.getStatusCodeValue());
        var res = response.getBody();
        assertEquals("403 FORBIDDEN", res.code());
        assertEquals("Your don't have permission to passing the payload", res.message());

    }
}