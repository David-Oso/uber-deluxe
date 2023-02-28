package com.example.uberDeluxe.util;

import com.example.uberDeluxe.exception.BusinessLogicException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class AppUtilities {
    public final int NUMBER_OF_ITEMS_PER_PAGE = 3;
    private static final String USER_VERIFICATION_BASE_URL="localhost:8080/api/v1/user/account/verify";

    public static String getMailTemplate(){
        try(BufferedReader reader = new BufferedReader(new FileReader(
                "C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\main\\resources\\welcome.txt"))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new BusinessLogicException(exception.getMessage());
        }
    }

    public static String getAdminMailTemplate(){
        try(BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\User\\IdeaProjects\\uberDeluxe\\uberDeluxe\\src\\main\\resources\\adminMail.txt"))){
            return reader.lines().collect(Collectors.joining());
        }catch (IOException exception){
            throw new BusinessLogicException(exception.getMessage());
        }
    }
    public static String generateVerificationLink(Long userId){
        return USER_VERIFICATION_BASE_URL+"?userId="+userId+"&token="+generateVerificationToken();
    }

    private static String generateVerificationToken(){
        return Jwts.builder()
                .setIssuer("uber_deluxe")
                .signWith(SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode(""))
    }

}
