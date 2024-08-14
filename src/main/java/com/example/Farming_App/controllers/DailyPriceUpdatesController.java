package com.example.Farming_App.controllers;

import com.example.Farming_App.dto.DataPrice;
import com.example.Farming_App.services.DailyPriceUpdatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/daily_price_updates")
@RequiredArgsConstructor
public class DailyPriceUpdatesController {
    private final DailyPriceUpdatesService dailyPriceUpdatesService;
    @GetMapping("/")
    public List<DataPrice> getDailyPrice() throws InterruptedException, MalformedURLException {
        return dailyPriceUpdatesService.getDailyPriceUpdates();
    }
}
