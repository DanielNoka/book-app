package org.springdemo.springproject.service;

public interface OTPService {

    String generateOtp(String email);
    boolean validateOtp(String email, String otp);
    void removeOtp(String email);
    void sendOtpEmail(String email, String otp);
}
