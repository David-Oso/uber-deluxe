package com.example.uberDeluxe.utils;

import com.example.uberDeluxe.exception.BusinessLogicException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

public class AppUtilities {
    public static final int NUMBER_OF_ITEMS_PER_PAGE = 3;
    private static final String USER_VERIFICATION_BASE_URL = "localhost:8080/api/v1/user/account/verify";
    public static final String WELCOME_MAIL_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\main\\resources\\welcome.txt";
    public static final String EMAIL_REGEX_STRING ="^[A-Za-z0-9+_.-]+@(.+)$";
    public static final String ADMIN_INVITE_MAIL_TEMPLATE_LOCATION = "C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\main\\resources\\adminMail.txt";
    public static final String JSON_CONSTANT = "json";
    public static final String TRANSPORT_MODE = "driving";
    public static final String UBER_DELUXE_TEST_IMAGE = "C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\test\\resources\\IMG-20220727-WA0004.jpg";

    public static String getMailTemplate(){
        try(BufferedReader reader = new BufferedReader(new FileReader(
                WELCOME_MAIL_TEMPLATE_LOCATION))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new BusinessLogicException(exception.getMessage());
        }
    }
    public static String getAdminMailTemplate(){
        try(BufferedReader reader =
                new BufferedReader(new FileReader(ADMIN_INVITE_MAIL_TEMPLATE_LOCATION))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new BusinessLogicException(exception.getMessage());
        }
    }
    public static String generateVerificationLink(Long userId){
        return USER_VERIFICATION_BASE_URL+"?userId="+userId+"&token="+generateVerificationToken();
    }

    private static String generateVerificationToken() {
        return Jwts.builder()
                .setIssuer("uber_deluxe")
                .signWith(SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(""))
                .setIssuedAt(new Date())
                .compact();
    }
    public static boolean isValidToken(String token){
        return Jwts.parser()
                .isSigned(token);
    }

}
