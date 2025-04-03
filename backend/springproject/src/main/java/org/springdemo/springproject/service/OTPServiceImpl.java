package org.springdemo.springproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdemo.springproject.exception.EntityNotFoundException;
import org.springdemo.springproject.repository.UserRepository;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import static org.springdemo.springproject.util.Constants.EMAIL_REGEX;
import  static  org.springdemo.springproject.util.Constants.OTP_EXPIRATION_TIME;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTPServiceImpl implements OTPService{

    private final StringRedisTemplate redisTemplate;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    // Declare Random object as a class-level field
    private static final SecureRandom secureRandom = new SecureRandom(); // less predictable than Random

    public String generateOtp(String email) {

        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        userRepository.findByEmail(email).
          orElseThrow(()-> new EntityNotFoundException("User not found"));

        String otp = String.valueOf(secureRandom.nextInt(900000) + 100000); // 6-digit OTP

        log.info("Saving OTP: {} for email: {}", otp, email);

        try {
            redisTemplate.opsForValue().set(email, otp, OTP_EXPIRATION_TIME, TimeUnit.MINUTES);
        }
        catch (Exception e) {
            log.error("Error while connecting to Redis1: {}", e.getMessage(), e);
        }

        return otp;
    }

    public boolean validateOtp(String email, String otp) {
        try {
            String storedOtp = redisTemplate.opsForValue().get(email);
            return storedOtp != null && storedOtp.equals(otp);
        } catch (Exception e) {
            log.error("Redis connection error while validating OTP: {}", e.getMessage());
            return false;
        }
    }

    public void sendOtpEmail(String email, String otp) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Your OTP Code");
            message.setText("Your OTP is: " + otp + ". It expires in 10 minutes.");
            mailSender.send(message);
            log.info("OTP send successfully to email {}" ,email);
    }

    public void removeOtp(String email) {
        try {
            redisTemplate.delete(email);
        }catch (Exception e) {
            log.error("Error while removing OTP: {}", e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }

}

