package com.order.api.utill;

import com.order.api.exception.ServiceException;
import com.order.api.form.JoinForm;
import org.springframework.util.StringUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean isValidPhoneNumber(String number) {
        if (!StringUtils.hasText(number)) {
            return false;
        }
        Pattern pattern = Pattern.compile("\\d{3}-\\d{4}-\\d{4}");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static void isPhoneNumberPattern(String phoneNumber) {
        if (!ValidationUtils.isValidPhoneNumber(phoneNumber)) {
            throw new ServiceException("핸드폰 번호 형식이 올바르지 않습니다.");
        }
    }

    public static void isEmailPattern(String email) {
        if (!ValidationUtils.isValidEmailAddress(email)) {
            throw new ServiceException("이메일 형식이 올바르지 않습니다");
        }
    }

    public static void isNiceNamePattern(String nickname) {
        String pattern = "^([a-z]){5,30}$"; // 소문자만 허용
        Matcher matcher = Pattern.compile(pattern).matcher(nickname);

        if (!matcher.matches()) {
            throw new ServiceException("닉네임은 5~30자 사이의 영어 소문자로만 사용해주세요.");
        }
    }
    public static void isNamePattern(String name) {
        String pattern = "^([ㄱ-ㅎ가-힣a-zA-Z]){5,20}$"; // 한글,영대소문자.
        Matcher matcher = Pattern.compile(pattern).matcher(name);

        if (!matcher.matches()) {
            throw new ServiceException("이름은 5~20자 사이의 영어 소문자 혹은 숫자만의 조합을 사용해주세요.");
        }
    }

    public static void isPasswordPattern(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[~`!@#$%\\^\\[\\]&*()-])(?=.*[a-z])(?=.*[A-Z]).{10,20}$"; // 영어대소문자 + 특수문자

        Matcher matcher1 = Pattern.compile(passwordPattern).matcher(password);

        if (!matcher1.matches() )
            throw new ServiceException("비밀번호는 10~20자 사이에 영문자,소문자,특수문자,숫자 각 1개이상 조합을 사용해주세요.");
    }
}