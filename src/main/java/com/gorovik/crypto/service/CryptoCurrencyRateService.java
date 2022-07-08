package com.gorovik.crypto.service;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.model.CurrencyStatistics;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface CryptoCurrencyRateService {

    /**
     * Saves a list of currency rates to DB.
     *
     * @param rates list of currency rates.
     * @return the amount of saved rates.
     */
    long saveAll(List<CryptoCurrencyRate> rates);

    /**
     * @return codes of all currencies.
     */
    List<String> findAllCryptoCurrencies();

    /**
     * Calculates the oldest, newest, minimal and maximal rate of a currency.
     *
     * @param code the code of a currency.
     * @return CurrencyStatistics object.
     * @see CurrencyStatistics
     */
    CurrencyStatistics calculateStats(String code);

    /**
     * @return currencies descending by the normalized range of the whole period.
     */
    List<String> getCurrenciesTopList();

    /**
     * @param date the day to calculate the normalized range for each currency.
     * @return currencies descending by the normalized range of the specified day.
     */
    List<String> getCurrenciesTopList(Date date);

    /**
     * Reads currency rates from the file and saves to the DB.
     *
     * @return the amount of saved rates;
     */
    long uploadRatesFromFile(MultipartFile file);
}
