package com.example.Farming_App.controllers;

import com.example.Farming_App.constants.AccountsConstants;
import com.example.Farming_App.dto.*;
import com.example.Farming_App.entity.Account;
import com.example.Farming_App.services.AuthenticationService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
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
import java.util.Arrays;
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
    public ResponseEntity<ResponseDto> signup(@Valid @RequestBody SignUpRequest signUpRequest){
        Account account=authenticationService.signup(signUpRequest);
        if(account!=null)
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(new ResponseDto(AccountsConstants.STATUS_201,AccountsConstants.MESSAGE_201));
        else
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.MESSAGE_500));
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
    public ResponseEntity<JwtAuthenticationResponse> signin(@Valid @RequestBody SignInRequest signInRequest){
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
    public ResponseEntity<ResponseDto> resetPassword(@Email @RequestParam String mail,@Size(min = 5,max = 15) @RequestParam String newPassword){
        int isResetPassword=authenticationService.resetPassword(mail,newPassword);
        if(isResetPassword==1){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200,AccountsConstants.STATUS_200));
        }
        else
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ResponseDto(AccountsConstants.STATUS_500,AccountsConstants.STATUS_500));
    }

    @PostMapping("/google")
    public ResponseEntity<?> authenticationGoogle(@RequestBody String authCode) throws IOException {

//        // Trao đổi authCode lấy access token từ Firebase
//        RestTemplate restTemplate = new RestTemplate();
//        String tokenUrl = "https://oauth2.googleapis.com/token";
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("code", authCode);
//        params.add("client_id", "311763214868-sldhunn1fd07ei8jir79u1sell5kajs8.apps.googleusercontent.com");
//        params.add("client_secret", "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");
//        params.add("redirect_uri", "http://localhost:8080/login/oauth2/code/google");
//        params.add("grant_type", "authorization_code");
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//        ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, requestEntity, Map.class);
//        Map<String, Object> responseBody = response.getBody();
//
//        // Lấy access token từ phản hồi
//        String accessToken = (String) responseBody.get("access_token");
//
//        // Sử dụng access token để truy cập các tài nguyên được bảo vệ
//        // ...
//
//        return ResponseEntity.ok(responseBody);

        // (Receive authCode via HTTPS POST)
        try {
//            String CLIENT_SECRET_FILE = "C:\\Users\\duykh\\Desktop\\Farming-App\\client_secret.json";
//            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
//                    JacksonFactory.getDefaultInstance(), new FileReader(CLIENT_SECRET_FILE));
            List<String> SCOPES = Arrays.asList(
                    "https://www.googleapis.com/auth/userinfo.profile",
                    "https://www.googleapis.com/auth/userinfo.email"
            );

            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    "1023349426630-b91i8avprktfh6d8910p0llhdu88tj3b.apps.googleusercontent.com",
                    "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",
                    authCode,
                    "http://localhost:8080/api/v1/auth/google")
                    .execute();
            String accessToken = tokenResponse.getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessToken);
        } catch (IOException e) {
            e.printStackTrace();
            // Log the error and return a meaningful response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error exchanging auth code for access token: " + e.getMessage());
        }

    }

}
