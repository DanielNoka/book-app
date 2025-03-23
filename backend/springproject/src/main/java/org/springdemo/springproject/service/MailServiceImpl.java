package org.springdemo.springproject.service;

import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailServiceImpl implements  MailService {

    private final JavaMailSender mailSender;

    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    @Async
    public void sendMail(List<String> emailList, String bookTitle) {

        // Validate email addresses
        List<String> validEmails = emailList.stream()
                .filter(email -> email != null && isValidEmail(email))
                .toList();

        if (validEmails.isEmpty()) {
            log.info("No valid email addresses found. Skipping email sending.");
            return;
        }

        // Send email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(validEmails.toArray(new String[0])); // Convert List to array
            message.setSubject("New Book Added to Our Bookstore!");
            message.setText("Hello ;)\n" +
                    "We are excited to announce that a new book has been added to our bookstore: " + bookTitle + "\n" +
                    "Check it out and explore the amazing content we have to offer.\n" +
                    "Best regards,\n" +
                    "The BookStore Team");

            mailSender.send(message);
            log.info("Email sent successfully to: {} ", validEmails);
        } catch (Exception e) {
            log.error("Failed to send email: {} ",e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile(EMAIL_REGEX).matcher(email).matches();
    }
}
