package com.gorovik.crypto.file;

import com.gorovik.crypto.entity.CryptoCurrencyRate;
import com.gorovik.crypto.exception.FileParsingException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileCurrencyRatesParser {

    private final CSVReader reader;
    public static final String ERROR_LINE_PARSING = "Invalid line in csv! There must be three items in line!";
    public static final String ERROR_RATE_PARSING = "Error parsing currency rate!";
    public static final String ERROR_TIME_MS_PARSING = "Error parsing currency time in milliseconds!";

    private FileCurrencyRatesParser(File file) throws IOException {
        reader = new CSVReader(new FileReader(file));
    }

    public static List<CryptoCurrencyRate> getRatesFromFile(File file) {
        try {
            return new FileCurrencyRatesParser(file).parseRates();
        } catch (CsvValidationException | IOException e) {
            throw new FileParsingException(e);
        }
    }

    private List<CryptoCurrencyRate> parseRates() throws CsvValidationException, IOException {
        reader.skip(1);
        List<CryptoCurrencyRate> result = new ArrayList<>();
        CryptoCurrencyRate cryptoCurrencyRate;
        String[] lineInArray;
        while ((lineInArray = reader.readNext()) != null) {
            cryptoCurrencyRate = parseLine(lineInArray);
            result.add(cryptoCurrencyRate);
        }
        reader.close();
        return result;
    }

    private CryptoCurrencyRate parseLine(String[] line) {
        if (line == null) {
            return null;
        }
        if (line.length != 3) {
            throw new FileParsingException(ERROR_LINE_PARSING);
        }

        double rate = parseCurrencyRate(line[2]);
        long timeInMilliSeconds = parseTimeInMilliseconds(line[0]);

        return new CryptoCurrencyRate(line[1], rate, new Date(timeInMilliSeconds));
    }

    private double parseCurrencyRate(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException ex) {
            throw new FileParsingException(ERROR_RATE_PARSING, ex);
        }
    }

    private long parseTimeInMilliseconds(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            throw new FileParsingException(ERROR_TIME_MS_PARSING, ex);
        }
    }
}
