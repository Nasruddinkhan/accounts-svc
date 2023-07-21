package com.sa.tawuniya.assingment.account.util;

import com.sa.tawuniya.assingment.account.exception.AccountException;
import com.sa.tawuniya.assingment.account.exception.enums.AccountResponseErrorCode;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.math.RoundingMode;

public class CommonUtil {
    public static final String DD_DOT_MM_DOT_YYYY = "dd.MM.yyyy";
    public static final String SHA_256 = "SHA-256";

    private CommonUtil(){}
    public static LocalDate getLocalDate(String date){
       return LocalDate.parse(date, DateTimeFormatter.ofPattern(DD_DOT_MM_DOT_YYYY));
    }

    public static BigDecimal getAmount(String amount){
        return new BigDecimal(amount).setScale(2,RoundingMode.HALF_UP);
    }

    public static String hashAccountNumber(String accountNumber)  {
         MessageDigest md;
        try {
            md = MessageDigest.getInstance(SHA_256);
        } catch (NoSuchAlgorithmException e) {
            throw new AccountException(AccountResponseErrorCode.UNEXPECTED_ERROR, e.getMessage());
        }
        byte[] hashBytes = md.digest(accountNumber.getBytes(StandardCharsets.UTF_8));
        return bytesToHexString(hashBytes);
    }

    private static String bytesToHexString(byte[] bytes) {
        return new StringBuilder()
                .append(bytesToHex(bytes))
                .toString();
    }

    private static String bytesToHex(byte[] bytes) {
        return bytesToHex(bytes, 0, bytes.length);
    }

    private static String bytesToHex(byte[] bytes, int start, int end) {
        return new StringBuilder((end - start) * 2)
                .append(bytes)
                .chars()
                .mapToObj(b -> String.format("%02x", b))
                .collect(Collectors.joining());
    }

    public static boolean isDateInRange(LocalDate date, LocalDate fromDate, LocalDate toDate) {
        return !date.isBefore(fromDate) && !date.isAfter(toDate);
    }

    public static boolean isAmountInRange(BigDecimal amount, BigDecimal fromAmount, BigDecimal toAmount) {
        return amount.compareTo(fromAmount) >= 0 && amount.compareTo(toAmount) <= 0;
    }

}
