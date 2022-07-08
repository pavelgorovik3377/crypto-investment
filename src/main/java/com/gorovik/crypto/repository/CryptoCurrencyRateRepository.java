package com.gorovik.crypto.repository;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;

public interface CryptoCurrencyRateRepository extends CrudRepository<CryptoCurrencyRate, Long> {

    @Query("SELECT DISTINCT cc.currencyCode FROM CryptoCurrencyRate cc")
    List<String> findAllCryptoCurrencyCodes();

    CryptoCurrencyRate findTopByCurrencyCodeOrderByDateAsc(String currencyCode);

    CryptoCurrencyRate findTopByCurrencyCodeOrderByDateDesc(String currencyCode);

    CryptoCurrencyRate findTopByCurrencyCodeOrderByRateAsc(String currencyCode);

    CryptoCurrencyRate findTopByCurrencyCodeOrderByRateDesc(String currencyCode);

    @Query("select (max(cc.rate)-min(cc.rate))/min(cc.rate) from CryptoCurrencyRate cc where cc.currencyCode = :currencyCode")
    Double getNormByCurrencyCode(@Param("currencyCode") String currencyCode);

    @Query("select (max(cc.rate)-min(cc.rate))/min(cc.rate) from CryptoCurrencyRate cc where cc.currencyCode = :currencyCode and cc.date between :dateMin and :dateMax")
    Double getNormByCurrencyCodeAndDateBetween(@Param("currencyCode") String currencyCode, @Param("dateMin") Date dateMin, @Param("dateMax") Date dateMax);
}
