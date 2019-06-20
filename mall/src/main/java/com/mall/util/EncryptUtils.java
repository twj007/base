package com.mall.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/***
 **@project: base
 **@description: include jwt, aes etc
 **@Author: twj
 **@Date: 2019/06/20
 **/
public class EncryptUtils {

    @Value("${project.jwt.private.key}")
    private static String key;


    public static String serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        String string = byteArrayOutputStream.toString("ISO-8859-1");
        objectOutputStream.close();
        byteArrayOutputStream.close();
        return string;
    }
    public static Object serializeToObject(String str) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        byteArrayInputStream.close();
        return object;
    }

    public static String encode(String issuer, String info){
        String token = null;
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,100);
        Date expiresDate = nowTime.getTime();

        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            //Issuser需手动设置
            token = JWT.create()
                    .withIssuer(issuer)
                    .withClaim("userId", info)
                    .withExpiresAt(expiresDate)
                    .sign(algorithm);
            //System.out.println(token);

        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;

    }


    public static String encode(String info){
        String token = null;
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,100);
        Date expiresDate = nowTime.getTime();

        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            //Issuser需手动设置
            token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", info)
                    .withExpiresAt(expiresDate)
                    .sign(algorithm);
            System.out.println(token);

        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return token;
    }

    public static boolean verify(String issuer, String token){
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        if(jwt == null){
            return false;
        }
        return true;
    }



    public static boolean verify(String token){
        Algorithm algorithm = Algorithm.HMAC256(key);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
        DecodedJWT jwt = verifier.verify(token);
        if(jwt == null){
            return false;
        }
        return true;
    }

    public static String decode(String issuer, String token){
        String jwtStr = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwtStr = jwt.getClaim("userId").asString();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return jwtStr;
    }


    public static String decode(String token){
        String jwtStr = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(key);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("auth0")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            jwtStr = jwt.getClaim("userId").asString();
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
        }
        return jwtStr;
    }
}
