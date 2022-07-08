package com.gorovik.crypto.rest;

import com.gorovik.crypto.model.CurrencyStatistics;
import com.gorovik.crypto.service.CryptoCurrencyRateService;
import com.gorovik.crypto.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/currency")
public class CryptoCurrencyController {

    @Autowired
    private CryptoCurrencyRateService cryptoCurrencyRateService;

    @GetMapping(value = "/all")
    public List<String> getCryptoCurrencyCodes() {
        return cryptoCurrencyRateService.findAllCryptoCurrencies();
    }

    @GetMapping(value = "/stats")
    public CurrencyStatistics getCryptoCurrencyStats(@RequestParam(name = "code") String code) {
        return cryptoCurrencyRateService.calculateStats(code);
    }

    @GetMapping(value = "/top-list")
    public List<String> getCryptoCurrencyTopList(@RequestParam(name = "date", required = false) String dateString) {
        Date date = DateUtils.parseDate(dateString, DateUtils.DATE_FORMAT);
        return date == null ?
                cryptoCurrencyRateService.getCurrenciesTopList() :
                cryptoCurrencyRateService.getCurrenciesTopList(date);
    }

}
