package com.gorovik.crypto.service;

import com.gorovik.crypto.model.CurrencyStatistics;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CryptoCurrencyRateService {

    List<String> findAllCryptoCurrencies();

    CurrencyStatistics calculateStats(String code);

    List<String> getCurrenciesTopList();

    List<String> getCurrenciesTopList(Date date);

    int uploadRatesFromFile();
}
