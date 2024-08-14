package com.example.Farming_App.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Email",
        description = "Schema to hold email submission information"
)
@Data
public class MailDto {

    @Schema(
            description = "The email submission title"
    )
    private String subject;

    @Schema(
            description = "The email submission content"
    )
    private String message;
}
