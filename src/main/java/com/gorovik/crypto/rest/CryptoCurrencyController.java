package com.gorovik.crypto.rest;

import com.gorovik.crypto.constants.RestConstants;
import com.gorovik.crypto.exception.BadRequestException;
import com.gorovik.crypto.model.CurrencyStatistics;
import com.gorovik.crypto.service.CryptoCurrencyRateService;
import com.gorovik.crypto.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(RestConstants.CURRENCY)
public class CryptoCurrencyController {

    @Autowired
    private CryptoCurrencyRateService cryptoCurrencyRateService;

    @GetMapping(value = RestConstants.ALL)
    public List<String> getCryptoCurrencyCodes() {
        return cryptoCurrencyRateService.findAllCryptoCurrencies();
    }

    @GetMapping(value = RestConstants.STATS)
    public CurrencyStatistics getCryptoCurrencyStats(@RequestParam(name = RestConstants.CODE) String code) {
        return cryptoCurrencyRateService.calculateStats(code);
    }

    @GetMapping(value = RestConstants.TOP_LIST)
    public List<String> getCryptoCurrencyTopList() {
        return cryptoCurrencyRateService.getCurrenciesTopList();
    }

    @GetMapping(value = RestConstants.TOP_LIST_DAILY)
    public List<String> getCryptoCurrencyTopListByDay(@RequestParam(name = RestConstants.DATE) String dateString) {
        Date date = DateUtils.parseDate(dateString, DateUtils.DATE_FORMAT);
        return cryptoCurrencyRateService.getCurrenciesTopList(date);
    }

    @PostMapping(value = RestConstants.UPLOAD)
    public long getCryptoCurrencyTopListByDay(@RequestParam(RestConstants.FILE) MultipartFile file) {
        return cryptoCurrencyRateService.uploadRatesFromFile(file);
    }

    @ExceptionHandler(BadRequestException.class)
    public void handleBadRequestException(BadRequestException ex, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
