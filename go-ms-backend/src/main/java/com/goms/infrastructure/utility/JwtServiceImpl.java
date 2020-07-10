package com.goms.infrastructure.utility;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class JwtServiceImpl {

  public static void main(String[] args) throws JOSEException, ParseException {
    JwtServiceImpl test = new JwtServiceImpl();
    byte[] secret =
        Base64.getDecoder()
            .decode(
                "MTIzNDU2Nzg5QUJDREVGR0hJSktMTU5PUFFSU1RVVlcxMjM0NTY3ODlBQkNERUZHSElKS0xNTk9QUVJTVFVWVw==");
    String token = test.generateToken(5, secret, 5L);
    System.out.println(token);
    System.out.println(test.validateToken(token, secret));
    Integer id = test.getUserIdFromToken(token, secret);
    System.out.println(id);
  }

  public String generateToken(final Integer userId, final byte[] secret, final Long expirationTime)
      throws JOSEException {
    JWTClaimsSet claims =
        new JWTClaimsSet.Builder()
            .subject(userId.toString())
            .issueTime(new Date())
            .expirationTime(new Date(TimeUnit.HOURS.toMillis(expirationTime)))
            .build();

    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
    SignedJWT signedJWT = new SignedJWT(header, claims);
    JWSSigner signer = new MACSigner(secret);

    signedJWT.sign(signer);
    System.out.println(signedJWT.getSignature().toString());
    return signedJWT.serialize();
  }

  public Integer getUserIdFromToken(final String token, final byte[] secret)
      throws ParseException {
    SignedJWT signedJWT = SignedJWT.parse(token);
    return Integer.parseInt(signedJWT.getJWTClaimsSet().getSubject());
  }

  public boolean validateToken(String token, final byte[] secret)
      throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(secret);
    SignedJWT signedJWT = SignedJWT.parse(token);
    return signedJWT.verify(verifier);
  }
}
