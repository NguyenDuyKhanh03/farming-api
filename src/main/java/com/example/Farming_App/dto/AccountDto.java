package com.example.Farming_App.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "Product",
        description = "Schema to hold account information"
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String mail;

    private String firstname;
    private String lastname;
}
