package com.sa.tawuniya.assingment.account.service;

import com.sa.tawuniya.assingment.account.model.dto.AuthRequestDto;

import java.util.Map;

public interface AuthService {
     Map<String, String> authRequest(AuthRequestDto authRequestDto);

}
