package com.sa.tawuniya.assingment.account.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sa.tawuniya.assingment.account.logging.LogRequestResponse;
import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.service.StatementService;
import com.sa.tawuniya.assingment.account.validation.RequestPayloadValidation;
import io.micrometer.observation.annotation.Observed;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping
@Observed(name = "statement-api")
@SecurityRequirement(name = "account-api")
public class StatementController {
    private final StatementService statementService;
    private final RequestPayloadValidation requestPayloadValidation;
    @GetMapping("/statements")
    @LogRequestResponse
    public ResponseEntity<AccountDto> getStatements(
            @RequestParam(name = "accountId") Long accountId,
            @RequestParam(name = "fromDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(name = "toDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") LocalDate toDate,
            @RequestParam(name = "fromAmount", required = false) BigDecimal fromAmount,
            @RequestParam(name = "toAmount", required = false) BigDecimal toAmount){
        requestPayloadValidation.validatePayload(fromAmount,toAmount, fromDate,  toDate);
        return ResponseEntity.ok( statementService.getStatements(accountId ,  fromDate,  toDate,fromAmount,toAmount));
    }

}
