package com.example.Farming_App.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class PaymentDto {
    @Getter
    @Setter
    @Builder
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;


        public VNPayResponse(String code, String message, String paymentUrl) {
            this.code = code;
            this.message = message;
            this.paymentUrl = paymentUrl;
        }

    }
}
