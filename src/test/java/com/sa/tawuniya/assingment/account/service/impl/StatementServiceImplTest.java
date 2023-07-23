package com.sa.tawuniya.assingment.account.service.impl;

import com.sa.tawuniya.assingment.account.helper.StatementHelper;
import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.dto.StatementDto;
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
import static org.mockito.Mockito.when;

class StatementServiceImplTest {

    @InjectMocks
    StatementServiceImpl statementService;
    @Mock
    StatementHelper statementHelper;


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
    void getStatementsById(){
        when(statementHelper.getStatementDto(Mockito.any())).thenReturn(accountDto);
        var result =  statementService.getStatements(1L,null, null, null, null);
        assertEquals(accountDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(accountDto.getAccountType(), result.getAccountType());
        assertEquals(accountDto.getStatements(), result.getStatements());
    }

    @Test
    void getStatementsDateRange(){
        when(statementHelper.getStatementOfDateRange(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(accountDto);
        var result =  statementService.getStatements(1L,LocalDate.parse("2020-07-01"), LocalDate.parse("2020-10-14"), null, null);
        assertEquals(accountDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(accountDto.getAccountType(), result.getAccountType());
        assertEquals(accountDto.getStatements(), result.getStatements());
    }


    @Test
    void getStatementsAll(){
        when(statementHelper.getStatementDateRangeAndAmtRange(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(accountDto);
        var result =  statementService.getStatements(1L,LocalDate.parse("2020-07-01"), LocalDate.parse("2020-10-14"), new BigDecimal("196.8"), new BigDecimal("869.97"));
        assertEquals(accountDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(accountDto.getAccountType(), result.getAccountType());
        assertEquals(accountDto.getStatements(), result.getStatements());
    }

    @Test
    void getStatementsAmountRange(){

        when(statementHelper.getStatementOfAmountRange(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(accountDto);
        var result =  statementService.getStatements(1L,null, null, new BigDecimal("196.8"), new BigDecimal("869.97"));
        assertEquals(accountDto.getAccountNumber(), result.getAccountNumber());
        assertEquals(accountDto.getAccountType(), result.getAccountType());
        assertEquals(accountDto.getStatements(), result.getStatements());
    }
}