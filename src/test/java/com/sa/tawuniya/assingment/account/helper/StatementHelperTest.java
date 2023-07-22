package com.sa.tawuniya.assingment.account.helper;

import com.sa.tawuniya.assingment.account.mapper.AccountMapper;
import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.dto.StatementDto;
import com.sa.tawuniya.assingment.account.model.entity.Account;
import com.sa.tawuniya.assingment.account.model.entity.Statement;
import com.sa.tawuniya.assingment.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class StatementHelperTest {


    @InjectMocks
    StatementHelper statementHelper;
    AccountDto accountDto;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountMapper accountMapper;

    Account account;
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

        account = new Account(1L, "saving", "123456",  List.of(
                new Statement(1L,null, "2020-10-14", "196.8"),
                new Statement(2L, null, "2020-07-01", "869.97")
        ));

    }


    @Test
    void getStatementDto() {

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.convertAccountToDto(Mockito.any())).thenReturn(accountDto);
       AccountDto accountDto1 = statementHelper.getStatementDto(1L);
        assertEquals(accountDto.getAccountType(), accountDto1.getAccountType());
        assertEquals(accountDto.getAccountNumber(), accountDto1.getAccountNumber());
    }


    @Test
    void getStatementDateRange() {
        var account =  Account.builder()
                .id(1L)
                .statements(List.of()).accountNumber("123456").accountType("saving")
                .build();
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.convertAccountToDto(Mockito.any())).thenReturn(accountDto);
        AccountDto accountDto1 = statementHelper.getStatementOfDateRange(1L,LocalDate.parse("2020-07-01"), LocalDate.parse("2020-10-14"));
        assertEquals(accountDto.getAccountType(), accountDto1.getAccountType());
        assertEquals(accountDto.getAccountNumber(), accountDto1.getAccountNumber());
    }

    @Test
    void getStatementAmtRange() {
        var account =  Account.builder()
                .id(1L)
                .statements(List.of()).accountNumber("123456").accountType("saving")
                .build();
        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.convertAccountToDto(Mockito.any())).thenReturn(accountDto);
        AccountDto accountDto1 = statementHelper.getStatementOfAmountRange(1L,new BigDecimal("196.8"), new BigDecimal("1000.8"));
        assertEquals(accountDto.getAccountType(), accountDto1.getAccountType());
        assertEquals(accountDto.getAccountNumber(), accountDto1.getAccountNumber());
    }


    @Test
    void getThreeStatementDto() {

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.convertAccountToDto(Mockito.any())).thenReturn(accountDto);
        AccountDto accountDto1 = statementHelper.getStatementOfThreeMonth(1L);
        assertEquals(accountDto.getAccountType(), accountDto1.getAccountType());
        assertEquals(accountDto.getAccountNumber(), accountDto1.getAccountNumber());
    }
    @Test
    void getStatementDateRangeAndAmtRange() {

        Mockito.when(accountRepository.findById(Mockito.any())).thenReturn(Optional.of(account));
        Mockito.when(accountMapper.convertAccountToDto(Mockito.any())).thenReturn(accountDto);
        AccountDto accountDto1 = statementHelper.getStatementDateRangeAndAmtRange(1L,LocalDate.parse("2020-07-01"), LocalDate.parse("2020-10-14"), new BigDecimal("196.8"), new BigDecimal("1000.8"));
        assertEquals(accountDto.getAccountType(), accountDto1.getAccountType());
        assertEquals(accountDto.getAccountNumber(), accountDto1.getAccountNumber());
    }

}

