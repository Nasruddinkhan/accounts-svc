package com.sa.tawuniya.assingment.account.model.dto;

import java.util.List;

public record User(String username, String password, List<String> roles) {
}