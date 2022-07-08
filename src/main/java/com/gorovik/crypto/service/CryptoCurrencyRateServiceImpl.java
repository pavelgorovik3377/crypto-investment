package com.gorovik.crypto.service;

import com.gorovik.crypto.constants.Constants;
import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.exception.BadRequestException;
import com.gorovik.crypto.file.FileCurrencyRatesProvider;
import com.gorovik.crypto.model.CurrencyStatistics;
import com.gorovik.crypto.repository.CryptoCurrencyRateRepository;
import com.gorovik.crypto.utils.DateUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CryptoCurrencyRateServiceImpl implements CryptoCurrencyRateService {

    public static final String NO_CURRENCY_FORMAT = "Currency %s does not exist!";

    @Autowired
    private CryptoCurrencyRateRepository cryptoCurrencyRateRepository;

    @Override
    public long saveAll(List<CryptoCurrencyRate> rates) {
        Iterable<CryptoCurrencyRate> result = cryptoCurrencyRateRepository.saveAll(rates);
        return StreamSupport.stream(result.spliterator(), false).count();
    }

    @Override
    public List<String> findAllCryptoCurrencies() {
        return cryptoCurrencyRateRepository.findAllCryptoCurrencyCodes();
    }

    @Override
    public CurrencyStatistics calculateStats(String code) {
        CryptoCurrencyRate oldest = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByDateAsc(code);
        CryptoCurrencyRate newest = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByDateDesc(code);
        CryptoCurrencyRate min = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByCurrencyRateAsc(code);
        CryptoCurrencyRate max = cryptoCurrencyRateRepository.findTopByCurrencyCodeOrderByCurrencyRateDesc(code);
        if (ObjectUtils.allNotNull(oldest, newest, min, max)) {
            return new CurrencyStatistics(code, oldest.getCurrencyRate(), newest.getCurrencyRate(), min.getCurrencyRate(), max.getCurrencyRate());
        }
        throw new BadRequestException(String.format(NO_CURRENCY_FORMAT, code));
    }

    @Override
    public List<String> getCurrenciesTopList() {
        return getCurrenciesTopList(this::getNormalizedRange);
    }

    @Override
    public List<String> getCurrenciesTopList(Date date) {
        return getCurrenciesTopList(c -> getNormalizedRange(c, date));
    }

    @Override
    public long uploadRatesFromFile(MultipartFile file) {
        List<CryptoCurrencyRate> ratesFromFile = FileCurrencyRatesProvider.getRatesFromFile(file);
        return saveAll(ratesFromFile);
    }

    private List<String> getCurrenciesTopList(Function<String, Double> normGetter) {
        Comparator<Map.Entry<String, Double>> entryComparator = Map.Entry.comparingByValue();
        return findAllCryptoCurrencies().stream()
                .map(c -> Pair.of(c, normGetter.apply(c)))
                .filter(p -> p.getValue() != null)
                .sorted(entryComparator.reversed())
                .map(Pair::getKey)
                .collect(Collectors.toList());
    }

    private Double getNormalizedRange(String currency) {
        return cryptoCurrencyRateRepository.getNormByCurrencyCode(currency);
    }

    private Double getNormalizedRange(String currency, Date date) {
        Date dayStart = DateUtils.getDayStart(date);
        Date dayEnd = DateUtils.getDayEnd(dayStart);
        return cryptoCurrencyRateRepository.getNormByCurrencyCodeAndDateBetween(currency, dayStart, dayEnd);
    }
}
