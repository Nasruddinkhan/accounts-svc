package com.sa.tawuniya.assingment.account.service;

import com.sa.tawuniya.assingment.account.model.dto.AccountDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface StatementService {


    AccountDto getStatements( Long accountId, final LocalDate fromDate, final LocalDate toDate, final BigDecimal fromAmount, final BigDecimal toAmount);
}
