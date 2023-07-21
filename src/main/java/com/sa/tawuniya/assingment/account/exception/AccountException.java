package com.sa.tawuniya.assingment.account.exception;

import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountException extends RuntimeException {
    private final AccountResponseErrorCode error;
    private final String msg;


    public AccountException(AccountResponseErrorCode error, String msg) {
        this.error = error;
        this.msg = msg;
    }

    public AccountException(AccountResponseErrorCode error){
        this(error, error.getMsg());
    }
}