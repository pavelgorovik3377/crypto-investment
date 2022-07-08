package com.gorovik.crypto.entity;

import com.gorovik.crypto.utils.DateUtils;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "crypto_currency_rate")
public class CryptoCurrencyRate {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_rate")
    private double rate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    public CryptoCurrencyRate() {
    }

    public CryptoCurrencyRate(String currencyCode, double rate, Date date) {
        this.currencyCode = currencyCode;
        this.rate = rate;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getRate() {
        return rate;
    }

    public Date getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String dateString = DateUtils.formatDate(date, DateUtils.DATE_TIME_FORMAT);
        return String.format("Currency: %s, rate: %.2f, date: %s", currencyCode, rate, dateString);
    }
}
