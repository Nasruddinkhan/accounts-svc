package com.sa.tawuniya.assingment.account.constants;

public class ErrorConstants {
    public static final String  FROM_DATE_END_DATE_MSG = "please provide the fromDate = %s and toDate = %s";
    public static final String  FROM_AMT_AND_TO_AMT_MSG = "please provide the fromAmount = %s and toAmount = %s";
    public static final String  AMT_RANGE_VAL_MSG = "Invalid amount range: fromAmount %s must be less than toAmount. %s";
    public static final String  DATE_RANGE_VAL_MSG = "Invalid date range: fromDate %s must be before or equal to toDate. %s";
    public static final String  PERMISSION_DENIED_MSG = "Permission denied: you don't have permission to pass any parameter";
    public static final String  NO_RECORD_FOUND = "No record found for this id %s";
    public static final String  INVALID_ARGUMENT = "Invalid argument type:  parameter must be %s of type of %s";
    public static final String  USER_NOT_FOUND = "No User Found: No user present for given name %s";
    private ErrorConstants(){

    }
}
