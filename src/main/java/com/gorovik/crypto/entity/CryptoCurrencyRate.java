package com.gorovik.crypto.entity;

import com.gorovik.crypto.utils.DateUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "crypto_currency_rate")
public class CryptoCurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_code")
    private String currencyCode;

    @Column(name = "currency_rate")
    private double currencyRate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    public CryptoCurrencyRate() {
    }

    public CryptoCurrencyRate(String currencyCode, double currencyRate, Date date) {
        this.currencyCode = currencyCode;
        this.currencyRate = currencyRate;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public double getCurrencyRate() {
        return currencyRate;
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

    public void setCurrencyRate(double currencyRate) {
        this.currencyRate = currencyRate;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        String dateString = DateUtils.formatDate(date, DateUtils.DATE_TIME_FORMAT);
        return String.format("Currency: %s, rate: %.2f, date: %s", currencyCode, currencyRate, dateString);
    }
}
