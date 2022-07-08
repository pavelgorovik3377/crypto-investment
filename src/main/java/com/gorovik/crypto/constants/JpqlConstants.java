package com.gorovik.crypto.constants;

public class JpqlConstants {

    public static final String GET_CODES = "SELECT DISTINCT cc.currencyCode FROM CryptoCurrencyRate cc";

    public static final String GET_NORM_BY_CODE = "select (max(cc.currencyRate) - min(cc.currencyRate)) / min(cc.currencyRate) from CryptoCurrencyRate cc where cc.currencyCode = :currencyCode";

    public static final String AND_DATE_IS_BETWEEN = " and cc.date between :dateMin and :dateMax";

    public static final String GET_NORM_BY_CODE_AND_DATES = GET_NORM_BY_CODE + AND_DATE_IS_BETWEEN;


    public static final String CURRENCY_CODE = "currencyCode";

    public static final String DATE_MIN = "dateMin";

    public static final String DATE_MAX = "dateMax";
}
