package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.AccountsConstants;
import com.example.Farming_App.dto.*;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.services.AuthenticationService;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
@Tag(
        name = "Authentication Controller",
        description = "Controller to handle user authentication operations such as signup, signin, and password reset."
)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @Operation(
            summary = "Create Account REST API",
            description = "REST API to create new account inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status CREATE",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        Account account = authenticationService.signup(signUpRequest);
        if (account != null)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
    }

    @Operation(
            summary = "Sign In REST API",
            description = "REST API to sign in an existing user inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status SignUp",
                    content = @Content(
                            schema = @Schema(implementation = JwtAuthenticationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }

    @Operation(
            summary = "Reset Password REST API",
            description = "REST API to reset the password of an existing user inside FarmingApp"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status Reset Password",
                    content = @Content(
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PutMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@Email @RequestParam String mail, @Size(min = 5, max = 15) @RequestParam String newPassword) {
        int isResetPassword = authenticationService.resetPassword(mail, newPassword);
        if (isResetPassword == 1) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.STATUS_200));
        } else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.STATUS_500));
    }

    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody String email) throws IOException, NoSuchAlgorithmException {
        return ResponseEntity.ok(authenticationService.signInGoogle(email));
    }

    @PostMapping("/login/google")
    public ResponseEntity<?> authenticationGoogle(@RequestBody String idTokenString) {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier;
        try {
            verifier = new GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory)
                    .setAudience(Collections.singletonList("1023349426630-b91i8avprktfh6d8910p0llhdu88tj3b.apps.googleusercontent.com"))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                String userId = payload.getSubject();  // ID của người dùng
                String email = payload.getEmail();
                return ResponseEntity.ok(email);
            }

            return ResponseEntity.ok("");
        } catch (IOException | GeneralSecurityException e) {
            ResponseEntity.ok(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}