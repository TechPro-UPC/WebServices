package com.techpro.upc.iam_service.infrastructure.hashing.bcrypt;

import com.techpro.upc.iam_service.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;


public interface BCryptHashingService extends HashingService, PasswordEncoder {
}
