package com.example.Farming_App.services;

import com.example.Farming_App.dto.DataPrice;

import java.net.MalformedURLException;
import java.util.List;

public interface DailyPriceUpdatesService {
    List<DataPrice> getDailyPriceUpdates() throws InterruptedException, MalformedURLException;
}
