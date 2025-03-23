package org.springdemo.springproject.service;

import java.util.List;

public interface MailService {
    void sendMail(List<String> emailList, String bookTitle);
}
