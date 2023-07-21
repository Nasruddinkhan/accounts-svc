package com.sa.tawuniya.assingment.account.helper;

import com.sa.tawuniya.assingment.account.exception.AccountException;
import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;
import com.sa.tawuniya.assingment.account.mapper.AccountMapper;
import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.dto.StatementDto;
import com.sa.tawuniya.assingment.account.repository.AccountRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static com.sa.tawuniya.assingment.account.constants.ErrorConstants.NO_RECORD_FOUND;
import static com.sa.tawuniya.assingment.account.util.CommonUtil.isAmountInRange;
import static com.sa.tawuniya.assingment.account.util.CommonUtil.isDateInRange;


@Service
@RequiredArgsConstructor
@Observed(name = "statement-by-id")
public class StatementHelper {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    public AccountDto getStatementDto(Long accountId){
        return accountRepository.findById(accountId)
                .map(accountMapper::convertAccountToDto)
                .orElseThrow(()->new AccountException(AccountResponseErrorCode.NOT_FOUND, String.format(NO_RECORD_FOUND, accountId)));
    }


    public AccountDto getStatementOfThreeMonth(Long accountId){
        final var endDate = Optional.ofNullable(getMaxDateFromStatement(accountId))
                .orElseThrow(()-> new AccountException(AccountResponseErrorCode.UNEXPECTED_ERROR));
        final var startDate =  endDate.minusYears(1);
        return getStatementOfDateRange( accountId, startDate, endDate );

    }

    public AccountDto getStatementOfAmountRange(Long accountId, BigDecimal fromAmount, BigDecimal toAmount){
        final Predicate<StatementDto> isAmountRangePredicate = stmt -> isAmountInRange(stmt.getAmount(), fromAmount, toAmount);
        final var accounts =  getStatementDto(accountId);
        accounts.setStatements(filterStatements(accounts.getStatements(), isAmountRangePredicate));
        return accounts;
    }

    public AccountDto getStatementOfDateRange(Long accountId, LocalDate fromDate, LocalDate toDate ){
        final var accounts =  getStatementDto(accountId);
        final Predicate<StatementDto> dateInRangePredicate = stmt -> isDateInRange(stmt.getDateField(), fromDate, toDate);
        accounts.setStatements(filterStatements(accounts.getStatements(), dateInRangePredicate));
        return accounts;
    }
    public AccountDto getStatementDateRangeAndAmtRange(final Long accountId, final LocalDate fromDate,final LocalDate toDate, final BigDecimal fromAmount, final BigDecimal toAmount ){
        final Predicate<StatementDto> dateInRangePredicate = stmt -> isDateInRange(stmt.getDateField(), fromDate, toDate);
        final Predicate<StatementDto> isAmountRangePredicate = stmt -> isAmountInRange(stmt.getAmount(), fromAmount, toAmount);
        final Predicate<StatementDto>  combineDto =  dateInRangePredicate.and(isAmountRangePredicate);
        final var accounts =  getStatementDto(accountId);
        accounts.setStatements(filterStatements(accounts.getStatements(), combineDto));
        return accounts;
    }
    private static List<StatementDto> filterStatements(List<StatementDto> statements, Predicate<StatementDto> predicate) {
        return statements.stream()
                .filter(predicate)
                .toList();
    }

    private LocalDate getMaxDateFromStatement(Long accountId){
        return getStatementDto(accountId).getStatements().stream()
                .map(StatementDto::getDateField)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

}
