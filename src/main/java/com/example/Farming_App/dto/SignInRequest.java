package com.example.Farming_App.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "SignIn",
        description = "Schema to hold login information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInRequest {

    @Schema(
            example = "example@gmail.com"
    )
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String mail;

    @Schema(
            description = "The password must be between 5 and 15 characters long"
    )
    @NotEmpty(message = "Password can not be a null or empty")
    private String password;
}
