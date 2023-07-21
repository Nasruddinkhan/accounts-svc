package com.sa.tawuniya.assingment.account.service.impl;

import com.sa.tawuniya.assingment.account.events.StatementEvent;
import com.sa.tawuniya.assingment.account.helper.StatementHelper;
import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.service.StatementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementHelper statementHelper;

    @Override
    public AccountDto getStatements(Long accountId, final LocalDate fromDate, final LocalDate toDate, final BigDecimal fromAmount, final BigDecimal toAmount) {

        return switch (StatementEvent.match(fromAmount, toAmount, fromDate, toDate)) {
            case ALL -> statementHelper.getStatementDateRangeAndAmtRange(accountId, fromDate, toDate, fromAmount, toAmount);
            case DATE_RANGE -> statementHelper.getStatementOfDateRange(accountId, fromDate, toDate);
            case AMT_RANGE -> statementHelper.getStatementOfAmountRange(accountId,  fromAmount,  toAmount);
            case BY_ACC_NO -> statementHelper.getStatementDto(accountId);
            default -> statementHelper.getStatementOfThreeMonth(accountId);
        };
    }
}
