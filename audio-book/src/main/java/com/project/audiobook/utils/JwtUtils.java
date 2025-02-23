package com.project.audiobook.utils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.project.audiobook.entity.Employee;
import com.project.audiobook.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtils {
//    private static final String SIGNER_KEY = "k8Kyj9yhxzlKSAJ1uY2m9dDzN3mbgmIT06H3vz/O7pEI2k+BRXNTFhK+WFk1hrz9";
//
//    public String generateToken(String email, List<String> roles) {
//        try {
//            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                    .subject(email)
//                    .issuer("btalk.com")
//                    .expirationTime(new Date(System.currentTimeMillis() + 3600000))
//                    .claim("scope", String.join(" ", roles))
//                    .build();
//
//            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
//            signedJWT.sign(new MACSigner(SIGNER_KEY));
//
//            return signedJWT.serialize();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to generate JWT", e);
//        }
//    }
//
//    public String parseToken(String token) {
//        try {
//            SignedJWT signedJWT = (SignedJWT) JWTParser.parse(token);
//            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
//            return claims.getSubject(); // Trả về email
//        } catch (ParseException e) {
//            throw new RuntimeException("Failed to parse JWT", e);
//        }
//    }
}
