package com.sa.tawuniya.assingment.account.model.dto.error;

public record AccountError(String errorCode, String message, String path, String url) {
}
