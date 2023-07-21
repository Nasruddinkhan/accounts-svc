package com.sa.tawuniya.assingment.account.validation;

import com.sa.tawuniya.assingment.account.exception.AccountException;
import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.sa.tawuniya.assingment.account.constants.ErrorConstants.*;

@Component
@Slf4j
public class RequestPayloadValidation {
    public void validatePayload(BigDecimal fromAmount, BigDecimal toAmount, LocalDate fromDate, LocalDate toDate){
        validateUserPermission(fromAmount,toAmount,fromDate, toDate);
        validateDate(fromDate, toDate);
        validateAmount(fromAmount, toAmount);
    }

    private void validateDate(LocalDate fromDate, LocalDate toDate) {
        if ( (Objects.isNull(fromDate) && Objects.nonNull(toDate) )||  (Objects.isNull(toDate) && Objects.nonNull(fromDate))){
            throw new AccountException(AccountResponseErrorCode.BAD_REQUEST,String.format(FROM_DATE_END_DATE_MSG,fromDate, toDate ) );
        }
        if (fromDate != null  && fromDate.isAfter(toDate)) {
            throw new AccountException(AccountResponseErrorCode.BAD_REQUEST, String.format(DATE_RANGE_VAL_MSG, fromDate, toDate));
        }
    }

    private void validateAmount(BigDecimal fromAmount, BigDecimal toAmount) {
        if ( (Objects.isNull(fromAmount) && Objects.nonNull(toAmount) )||  (Objects.isNull(toAmount) && Objects.nonNull(fromAmount))){
            throw new AccountException(AccountResponseErrorCode.BAD_REQUEST,String.format(FROM_AMT_AND_TO_AMT_MSG,fromAmount, toAmount ) );
        }
        if ( fromAmount != null && fromAmount.compareTo(toAmount) >= 0){
            throw new AccountException(AccountResponseErrorCode.BAD_REQUEST, String.format(AMT_RANGE_VAL_MSG,fromAmount, toAmount ));
        }
    }

    public void validateUserPermission(final BigDecimal fromAmount, final BigDecimal toAmount, final LocalDate fromDate, final LocalDate toDate){
        if ( (Objects.nonNull(fromAmount) || Objects.nonNull(toAmount) || Objects.nonNull(fromDate) || Objects.nonNull(toDate)) && "ROLE_USER".equalsIgnoreCase(getLoggedInUserId()) ){
            throw new AccountException(AccountResponseErrorCode.FORBIDDEN,PERMISSION_DENIED_MSG );
        }
    }

    public static String getLoggedInUserId() {
        var listOfRole =   SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority ::getAuthority).toList();
        return listOfRole.get(0);
    }
}
