package com.techpro.upc.iam_service.infrastructure.tokens.jwt;

import com.techpro.upc.iam_service.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


public interface BearerTokenService extends TokenService {


    String getBearerTokenFrom(HttpServletRequest token);


    String generateToken(Authentication authentication);
}