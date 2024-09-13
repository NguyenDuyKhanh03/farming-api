package com.example.Farming_App.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MailDto {

    private String subject;

    private String message;
}
