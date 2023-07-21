package com.sa.tawuniya.assingment.account.mapper;

import com.sa.tawuniya.assingment.account.model.dto.StatementDto;
import com.sa.tawuniya.assingment.account.model.entity.Statement;
import com.sa.tawuniya.assingment.account.util.CommonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StatementMapper {
    public List<StatementDto> convertStatementToDto(List<Statement> statements){
        return statements.stream()
                .map(this::getStatement)
                .toList();
    }

    private StatementDto getStatement(Statement statement){
     return StatementDto.builder()
                .amount(CommonUtil.getAmount(statement.getAmount()))
                .dateField(CommonUtil.getLocalDate(statement.getDateField()))
                .build();
    }
}
