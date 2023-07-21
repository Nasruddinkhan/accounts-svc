package com.sa.tawuniya.assingment.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AccountDto {

    private String accountType;
    private String accountNumber;
    private List<StatementDto> statements;

}
