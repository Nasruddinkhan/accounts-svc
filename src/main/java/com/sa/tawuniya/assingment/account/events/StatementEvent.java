package com.sa.tawuniya.assingment.account.events;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.sa.tawuniya.assingment.account.validation.RequestPayloadValidation.getLoggedInUserId;

public enum StatementEvent {
    ALL,
    DATE_RANGE,
    AMT_RANGE,
    LAST_THREE_MONTH,

    BY_ACC_NO;

    public static StatementEvent match(BigDecimal fromAmount, BigDecimal toAmount, LocalDate fromDate, LocalDate toDate) {
        if (Objects.nonNull(fromAmount) && Objects.nonNull(toAmount) && Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
            return ALL;
        } else if (Objects.nonNull(fromDate) && Objects.nonNull(toDate)) {
            return DATE_RANGE;
        } else if (Objects.nonNull(fromAmount) && Objects.nonNull(toAmount)) {
            return AMT_RANGE;
        } else {
           return  "ROLE_USER".equalsIgnoreCase(getLoggedInUserId()) ? LAST_THREE_MONTH : BY_ACC_NO;
        }
    }
}
