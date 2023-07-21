package com.sa.tawuniya.assingment.account.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StatementDto {


    @JsonIgnore
    private Long accountId;
    private LocalDate dateField;
    private BigDecimal amount;
}
