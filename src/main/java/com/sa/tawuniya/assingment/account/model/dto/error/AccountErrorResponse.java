package com.sa.tawuniya.assingment.account.model.dto.error;

import java.util.List;

public record AccountErrorResponse(String code, String message, List<AccountError> errors) {
}
