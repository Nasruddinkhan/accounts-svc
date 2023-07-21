package com.sa.tawuniya.assingment.account.exception;

import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;
import com.sa.tawuniya.assingment.account.model.dto.error.AccountError;
import com.sa.tawuniya.assingment.account.model.dto.error.AccountErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

import static com.sa.tawuniya.assingment.account.constants.ErrorConstants.INVALID_ARGUMENT;
import static com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode.BAD_REQUEST;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = {AccountException.class})
    public ResponseEntity<AccountErrorResponse> handleAccountException(AccountException accountException, HttpServletRequest request) {
        return this.createErrorResponse(accountException, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<AccountErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return this.createErrorResponse(new AccountException(BAD_REQUEST, String.format(INVALID_ARGUMENT, ex.getName(), ex.getRequiredType().getSimpleName()) ), request);

    }

    private ResponseEntity<AccountErrorResponse> createErrorResponse(AccountException accountException, HttpServletRequest request) {
        var errorReply = createAccountError(accountException, request);
        return ResponseEntity.status(Optional.ofNullable(HttpStatus.resolve(accountException.getError().getHttpStatus()))
                        .orElse(HttpStatus.BAD_REQUEST))
                .body(errorReply);
    }

    private AccountErrorResponse createAccountError(AccountException accountException, HttpServletRequest request) {
        var error = accountException.getError();
        var msg = accountException.getMsg();
        if (error == null) {
            error = AccountResponseErrorCode.UNEXPECTED_ERROR;
        }
        msg = StringUtils.isBlank(msg) ? error.getMsg() : msg;
        var status = Optional.ofNullable(HttpStatus.resolve(error.getHttpStatus())).orElse(HttpStatus.BAD_REQUEST);
        var errorList = List.of(new AccountError(error.getCode(), msg, request.getContextPath(), request.getRequestURI()));
        return new AccountErrorResponse(status.toString(), error.getMsg(), errorList);
    }
}
