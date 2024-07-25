/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.raven.logic;

/**
 *
 * @author Ca1
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RandomStringGenerator {
    public static String generateRandomString(String prefix) {
        // Lấy năm, tháng, ngày, giờ, phút, giây hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Lấy chữ số cuối cùng của năm, tháng, ngày, giờ, phút, giây
        String year = String.valueOf(now.getYear());
        String month = String.valueOf(now.getMonthValue());
        String day = String.valueOf(now.getDayOfMonth());
        String hour = String.valueOf(now.getHour());
        String minute = String.valueOf(now.getMinute());
        String second = String.valueOf(now.getSecond());

        // Kết hợp các chữ số cuối cùng
        String formattedDateTime = 
//                year.substring(year.length() - 1) +
//                month.substring(month.length() - 1) +
//                day.substring(day.length() - 1) +
                hour.substring(hour.length() - 1) +
                minute.substring(minute.length() - 1) +
                second.substring(second.length() - 1);

        // Tạo chuỗi ngẫu nhiên
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder();

        // Thêm 3 ký tự ngẫu nhiên
        for (int i = 0; i < 3; i++) {
            randomString.append(characters.charAt(random.nextInt(characters.length())));
        }

        // Kết hợp các chữ số cuối cùng và chuỗi ngẫu nhiên
//        String result = formattedDateTime + randomString.toString();
        String result = formattedDateTime + randomString.toString();

        return prefix+result;
    }
}
