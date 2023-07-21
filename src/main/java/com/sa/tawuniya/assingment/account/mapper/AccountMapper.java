package com.sa.tawuniya.assingment.account.mapper;

import com.sa.tawuniya.assingment.account.model.dto.AccountDto;
import com.sa.tawuniya.assingment.account.model.entity.Account;
import com.sa.tawuniya.assingment.account.util.CommonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final StatementMapper statementMapper;
    public AccountDto convertAccountToDto(Account account){
        return AccountDto.builder()
                .accountNumber(CommonUtil.hashAccountNumber(account.getAccountNumber()))
                .accountType(account.getAccountType())
                .statements(statementMapper.convertStatementToDto(account.getStatements()))
                .build();
    }
}
