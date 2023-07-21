package com.sa.tawuniya.assingment.account.controller;

import com.sa.tawuniya.assingment.account.logging.LogRequestResponse;
import com.sa.tawuniya.assingment.account.model.dto.AuthRequestDto;
import com.sa.tawuniya.assingment.account.service.AuthService;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth/v1")
@Slf4j
@Observed(name = "login-api")
@RequiredArgsConstructor
public class AuthRequest {

    private final AuthService authService;
    @LogRequestResponse
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authRequest(@RequestBody AuthRequestDto authRequestDto) {
        log.info("AuthResource.authRequest start {}", authRequestDto);
        var userRegistrationResponse = authService.authRequest(authRequestDto);
        log.info("AuthResource.authRequest end {}", userRegistrationResponse);
        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.OK);
    }


}
