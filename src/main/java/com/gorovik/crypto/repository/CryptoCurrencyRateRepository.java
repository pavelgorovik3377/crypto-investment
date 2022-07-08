package com.gorovik.crypto.repository;

import com.gorovik.crypto.constants.JpqlConstants;
import com.gorovik.crypto.entity.CryptoCurrencyRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CryptoCurrencyRateRepository extends CrudRepository<CryptoCurrencyRate, Long> {

    /**
     * Gets codes of all cryptocurrencies.
     *
     * @return cryptocurrency codes.
     */
    @Query(JpqlConstants.GET_CODES)
    List<String> findAllCryptoCurrencyCodes();

    /**
     * Gets the oldest currency rate.
     *
     * @param currencyCode the code of a currency.
     * @return the oldest currency rate.
     */
    CryptoCurrencyRate findTopByCurrencyCodeOrderByDateAsc(String currencyCode);

    /**
     * Gets the newest currency rate.
     *
     * @param currencyCode the code of a currency.
     * @return the newest currency rate.
     */
    CryptoCurrencyRate findTopByCurrencyCodeOrderByDateDesc(String currencyCode);

    /**
     * Gets the minimal currency rate.
     *
     * @param currencyCode the code of a currency.
     * @return the minimal currency rate.
     */
    CryptoCurrencyRate findTopByCurrencyCodeOrderByCurrencyRateAsc(String currencyCode);

    /**
     * Gets the maximal currency rate.
     *
     * @param currencyCode the code of a currency.
     * @return the maximal currency rate.
     */
    CryptoCurrencyRate findTopByCurrencyCodeOrderByCurrencyRateDesc(String currencyCode);

    /**
     * Gets the normalized range for a currency for the whole period.
     *
     * @param currencyCode the code of a currency.
     * @return the normalized range of a currency.
     */
    @Query(JpqlConstants.GET_NORM_BY_CODE)
    Double getNormByCurrencyCode(@Param(JpqlConstants.CURRENCY_CODE) String currencyCode);

    /**
     * Gets the normalized range for a currency for the specified period.
     *
     * @param currencyCode the code of a currency.
     * @param dateMin      the minimal date.
     * @param dateMax      the maximal date.
     * @return the normalized range of a currency.
     */
    @Query(JpqlConstants.GET_NORM_BY_CODE_AND_DATES)
    Double getNormByCurrencyCodeAndDateBetween(@Param(JpqlConstants.CURRENCY_CODE) String currencyCode, @Param(JpqlConstants.DATE_MIN) Date dateMin, @Param(JpqlConstants.DATE_MAX) Date dateMax);
}
