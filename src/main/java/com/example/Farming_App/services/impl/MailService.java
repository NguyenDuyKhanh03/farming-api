package com.example.Farming_App.services.impl;

import com.example.Farming_App.dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String formMail;

    private Map<String, Integer> verificationCodes = new HashMap<>();
    public void sendMail(String mail, MailDto mailDto){
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom(formMail);
        simpleMailMessage.setSubject(mailDto.getSubject());
        Random random = new Random();
        int code = 10000 + random.nextInt(90000);
        simpleMailMessage.setText(mailDto.getMessage()+code);
        simpleMailMessage.setTo(mail);

        verificationCodes.put(mail, code);

        mailSender.send(simpleMailMessage);
    }

    public boolean verifyCode(String mail, int code) {
        if (verificationCodes.containsKey(mail)) {
            int storedCode = verificationCodes.get(mail);
            if (storedCode == code) {
                verificationCodes.remove(mail);
                return true;
            }
        }
        return false;
    }

}
