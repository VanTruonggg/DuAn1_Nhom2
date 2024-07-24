/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.util;

import java.util.Random;

/**
 *
 * @author Pham Thu
 */
public class Helper {
    private static final String PREFIX = "VOUCHER"; // TIEN TO BAT DAU BANG HD 
    // REGEX ://d+...
    private static final int LENGTH = 5; // DO DAI CUA PHAN TU PHIA DANG SAU 
//    HD00124

    public static String generateRandomMaPGG() {
        Random random = new Random();
        // Sinh ra so ngau nhien randomw co do dai la length chu so
        int number = random.nextInt((int) Math.pow(10, LENGTH));
        // Format theo y mong muon 
        // Vs cu phap: Tien to HD + do dai chuoi dang sau la co dinh
        return PREFIX + String.format("%0" + LENGTH + "d", number);
    }
    public static final boolean checkDoDaiCuaChuoi(String inputString) {
        if (inputString.length() < 3 || inputString.length() > 20) {
            return false;
        } else {
            return true;
        }
    }
}
