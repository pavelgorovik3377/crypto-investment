package com.gorovik.crypto.file;

import com.gorovik.crypto.entity.CryptoCurrencyRate;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class AllFileCurrencyRatesProvider {

    public static List<CryptoCurrencyRate> getAllCurrencyRates() {
        List<File> fileNames = new FilesProvider("/data").getResourceFiles();
        return fileNames.stream()
                .map(FileCurrencyRatesParser::getRatesFromFile)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
