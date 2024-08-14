package com.example.Farming_App.request.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShippingOrderResponse {
    private int code;
    private String message;
    private Data data;
    private String messageDisplay;

    @lombok.Data
    public static class Data {

        @JsonProperty("order_code")
        private String orderCode;

        @JsonProperty("sort_code")
        private String sortCode;

        @JsonProperty("trans_type")
        private String transType;

        @JsonProperty("ward_encode")
        private String wardEncode;

        @JsonProperty("district_encode")
        private String districtEncode;

        @JsonProperty("total_fee")
        private int totalFee;

        @JsonProperty("expected_delivery_time")
        private String expectedDeliveryTime;


    }
}
