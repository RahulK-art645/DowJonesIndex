package com.rbc.dowjones.repository.util;

import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;
import java.util.Base64;

public class FileHashUtil {

    public static String generateHash(MultipartFile file){

        try{

            MessageDigest digest=MessageDigest.getInstance("SHA-256");
            byte[] hash=digest.digest(file.getBytes());

            return Base64.getEncoder().encodeToString(hash);
        }catch(Exception e){
            throw new RuntimeException("Unable to generate file hash");

        }
    }
}
