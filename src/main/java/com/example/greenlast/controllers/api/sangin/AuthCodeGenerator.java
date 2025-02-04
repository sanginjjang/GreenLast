package com.example.greenlast.controllers.api.sangin;

/**
 * Created on 2025-02-04 by 한상인
 */
import java.util.Random;

public class AuthCodeGenerator {
    public static String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10)); // 0~9 랜덤 숫자
        }
        return code.toString();
    }
}
