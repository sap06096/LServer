package com.example.LServer.module.common;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PhoneNumberUtil {

    /**
     * 전화 번호 하이픈 형식으로 변환
     * @param src
     * @return
     */
    public String convertTelNo(String src) {
        String mobTelNo = src;
        if (mobTelNo != null) {
            // 일단 기존 - 전부 제거
            mobTelNo = mobTelNo.replaceAll(Pattern.quote("-"), "");

            if (mobTelNo.length() == 11) {
                // 010-1234-1234
                mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 7) + "-" + mobTelNo.substring(7);

            } else if (mobTelNo.length() == 8) {
                // 1588-1234
                mobTelNo = mobTelNo.substring(0, 4) + "-" + mobTelNo.substring(4);
            } else {
                if (mobTelNo.startsWith("02")) { // 서울은 02-123-1234
                    mobTelNo = mobTelNo.substring(0, 2) + "-" + mobTelNo.substring(2, 5) + "-" + mobTelNo.substring(5);
                } else { // 그외는 012-123-1345
                    mobTelNo = mobTelNo.substring(0, 3) + "-" + mobTelNo.substring(3, 6) + "-" + mobTelNo.substring(6);
                }
            }
        }
        return mobTelNo;
    }


    /**
     * 하이픈 휴대폰 번호 유효성 검사
     * @param number
     * @return
     */
    public boolean validHyphenPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            System.out.println("Valid phone number: " + number);
            return true;
        } else {
            System.out.println("Invalid. Not the form XXX-XXXX-XXX: " + number);
            return false;
        }
    }

    /**
     * 11자리 휴대폰 번호 유효성 검사
     * @param number
     * @return
     */
    public boolean validPhoneNumber(String number) {
        Pattern pattern = Pattern.compile("^\\d{11}$");
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            System.out.println("Valid phone number: " + number);
            return true;
        } else {
            System.out.println("Invalid. Not the form XXX-XXXX-XXX: " + number);
            return false;
        }
    }

    /**
     * Base64로 인코딩되어 있는지와 휴대폰 번호 형식인지 확인하는 함수
     * @param encodedPhone
     * @return
     */
    public boolean isValidBase64Phone(String encodedPhone) {
        try {
            // URL-safe Base64 디코딩
            Base64.Decoder decoder = Base64.getUrlDecoder();
            byte[] decodedBytes = decoder.decode(encodedPhone.trim());
            // UTF-8로 디코딩
            String phone = new String(decodedBytes, StandardCharsets.UTF_8);
            return validPhoneNumber(phone);
        } catch (IllegalArgumentException e) {
            // Base64 디코딩에 실패한 경우
            return false;
        }
    }

}
