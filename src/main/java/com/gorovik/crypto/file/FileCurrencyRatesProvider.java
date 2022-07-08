package com.gorovik.crypto.file;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.exception.FileParsingException;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileCurrencyRatesProvider {

    public static List<CryptoCurrencyRate> getRatesFromFile(MultipartFile file) {
        try {
            return new FileCurrencyRatesParser(file).parseRates();
        } catch (CsvValidationException | IOException e) {
            throw new FileParsingException(e);
        }
    }

    public static List<CryptoCurrencyRate> getRatesFromFile(File file) {
        try {
            return new FileCurrencyRatesParser(file).parseRates();
        } catch (CsvValidationException | IOException e) {
            throw new FileParsingException(e);
        }
    }
}
