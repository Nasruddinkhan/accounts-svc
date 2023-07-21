package com.sa.tawuniya.assingment.account.exception.enums;

import lombok.Getter;

@Getter
public enum AccountResponseErrorCode {
    NOT_FOUND("Not Found", 404, "No record present for this request"),
    BAD_REQUEST("Bad Request", 400, "Invalid Payload"),
    UNEXPECTED_ERROR("Unexceptional Error ",500 ,"Unexceptional Error "),
    BAD_CREDENTIAL("Bad Credential", 401 , "You have enter incorrect username & password" ),
    TOKEN_EXPIRED("Token Expired",  403, "your jwt token is expired kindly re-generate or access your apis" ),
    FORBIDDEN("Permission Denied",  403, "Your don't have permission to passing the payload" );
    private final String code;
    private final int httpStatus;
    private final String msg;
    AccountResponseErrorCode(String code, int httpStatus, String msg) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.msg = msg;
    }
}
