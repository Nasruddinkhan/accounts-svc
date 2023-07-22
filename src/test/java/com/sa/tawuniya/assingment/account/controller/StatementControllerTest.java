package com.sa.tawuniya.assingment.account.controller;

import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.dto.StatementDto;
import com.sa.tawuniya.assingment.account.service.StatementService;
import com.sa.tawuniya.assingment.account.validation.RequestPayloadValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;


class StatementControllerTest {

    @InjectMocks
    StatementController statementController;

    @Mock
    StatementService statementService;

    @Mock
    RequestPayloadValidation requestPayloadValidation;

    AccountDto accountDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountDto = new AccountDto();
        accountDto.setAccountNumber("123456");
        accountDto.setAccountType("saving");
        accountDto.setStatements( List.of(
                new StatementDto(1L, LocalDate.parse("2020-10-14"), new BigDecimal("196.8")),
                new StatementDto(2L, LocalDate.parse("2020-07-01"), new BigDecimal("869.97"))
        ));
    }


    @Test
    void getStatements(){
        System.out.println(accountDto);
        Mockito.when(statementService.getStatements(Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any() )).thenReturn(accountDto);
        doNothing().when(requestPayloadValidation).validatePayload(Mockito.any(),Mockito.any(), Mockito.any(), Mockito.any());

        var res =  statementController.getStatements(1L, LocalDate.parse("2020-10-14"), LocalDate.parse("2020-07-01"), new BigDecimal("196.8"), new BigDecimal("869.97"));
        var result = res.getBody();
      assertEquals(200, res.getStatusCodeValue());
      assertEquals(accountDto.getAccountNumber(), result.getAccountNumber());
      assertEquals(accountDto.getAccountType(), result.getAccountType());

    }
}