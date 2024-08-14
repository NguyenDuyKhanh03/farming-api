package com.example.Farming_App.controllers;

import com.example.Farming_App.dto.MailDto;
import com.example.Farming_App.services.impl.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {
    private final MailService mailService;

    @PostMapping("/send/{mail}")
    public String sendMail(@PathVariable("mail") String mail, @RequestBody MailDto mailDto){
        try {
            mailService.sendMail(mail,mailDto);
            return "Successfully send the email!!!";
        }catch (Exception e){
            e.printStackTrace();
            return "Errol";
        }
    }
    @PostMapping("/verifyCode")
    public int verifyCode(@RequestParam String mail, @RequestParam int code) {
        if (mailService.verifyCode(mail, code)) {
            return 1;
        } else {
            return -1;
        }
    }
}
