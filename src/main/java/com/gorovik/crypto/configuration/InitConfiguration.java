package com.gorovik.crypto.configuration;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.file.FileCurrencyRatesProvider;
import com.gorovik.crypto.file.FilesProvider;
import com.gorovik.crypto.service.CryptoCurrencyRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class InitConfiguration {

    private static final String CSV_PATH = "/data";

    @Autowired
    private CryptoCurrencyRateService cryptoCurrencyRateService;

    @PostConstruct
    public void init() {
        List<CryptoCurrencyRate> allCurrencyRates = getAllCurrencyRatesFromFiles();
        cryptoCurrencyRateService.saveAll(allCurrencyRates);
    }

    private List<CryptoCurrencyRate> getAllCurrencyRatesFromFiles() {
        List<File> fileNames = new FilesProvider(CSV_PATH).getResourceFiles();
        return fileNames.stream()
                .map(FileCurrencyRatesProvider::getRatesFromFile)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
