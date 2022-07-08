package com.gorovik.crypto.service;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.file.AllFileCurrencyRatesProvider;
import com.gorovik.crypto.model.CurrencyStatistics;
import com.gorovik.crypto.repository.CryptoCurrencyRateRepository;
import com.gorovik.crypto.utils.DateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CryptoCurrencyRateServiceImpl implements CryptoCurrencyRateService {

    @Autowired
    private CryptoCurrencyRateRepository cryptoCurrencyRateRepository;

    @PostConstruct
    public void init() {
        List<CryptoCurrencyRate> allCurrencyRates = AllFileCurrencyRatesProvider.getAllCurrencyRates();
        cryptoCurrencyRateRepository.saveAll(allCurrencyRates);
    }

    @Override
    public List<String> findAllCryptoCurrencies() {
        return cryptoCurrencyRateRepository.findAllCryptoCurrencyCodes();
    }

    @Override
    public CurrencyStatistics calculateStats(String code) {
        return getStats(code);
    }

    @Override
    public List<String> getCurrenciesTopList() {
        Comparator<Map.Entry<String, Double>> entryComparator = Map.Entry.comparingByValue();
        return findAllCryptoCurrencies().stream()
                .map(c -> Pair.of(c, getNormalizedRange(c)))
                .sorted(entryComparator.reversed())
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getCurrenciesTopList(Date date) {
        Comparator<Map.Entry<String, Double>> entryComparator = Map.Entry.comparingByValue();
        return findAllCryptoCurrencies().stream()
                .map(c -> Pair.of(c, getNormalizedRange(c, date)))
                .sorted(entryComparator.reversed())
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public int uploadRatesFromFile() {
        return 0;
    }

    private double getNormalizedRange(String currency) {
        Double norm = cryptoCurrencyRateRepository.getNormByCurrencyCode(currency);
        if (norm == null) {
            String format = "Currency %s does not exist!";
            throw new IllegalStateException(String.format(format, currency));
        }
        return norm;
    }

    private double getNormalizedRange(String currency, Date date) {
        Date dayStart = DateUtils.getDayStart(date);
        Date dayEnd = DateUtils.getDayEnd(dayStart);
        Double norm = cryptoCurrencyRateRepository.getNormByCurrencyCodeAndDateBetween(currency, dayStart, dayEnd);
        if (norm == null) {
            String format = "Currency %s does not exist!";
            throw new IllegalStateException(String.format(format, currency));
        }
        return norm;
    }

    private CurrencyStatistics getStats(String currency) {
        CryptoCurrencyRate oldest = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByDateAsc(currency);
        CryptoCurrencyRate newest = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByDateDesc(currency);
        CryptoCurrencyRate min = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByRateAsc(currency);
        CryptoCurrencyRate max = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByRateDesc(currency);
        if (ObjectUtils.allNotNull(oldest, newest, min, max)) {
            return new CurrencyStatistics(oldest.getRate(), newest.getRate(), min.getRate(), max.getRate());
        }
        String format = "Currency %s does not exist!";
        throw new IllegalStateException(String.format(format, currency));
    }
}
