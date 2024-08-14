package com.example.Farming_App.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "SignUp",
        description = "Schema to hold registration information"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @Schema(
            example = "example@gmail.com"
    )
    @NotEmpty(message = "Email can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @NotEmpty(message = "Firstname can not be a null or empty")
    private String firstname;

    @NotEmpty(message = "Lastname can not be a null or empty")
    private String lastname;

    @Schema(
            description = "The password must be between 5 and 15 characters long"
    )
    @NotEmpty(message = "Password can not be a null or empty")
    @Size(min = 5,max = 15)
    private String password;
}
