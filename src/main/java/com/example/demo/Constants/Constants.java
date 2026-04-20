package com.example.demo.Constants;

import java.util.Dictionary;
import java.util.Hashtable;

public class Constants {

    public enum OrderStatus {
        PENDING,
        SHIPPED,
        DELIVERED,
        CANCELLED
    }

    public Dictionary<String, String> TaxByState = new Hashtable<String, String>(){
        {
            put("AL", "4%");
            put("AK", "0%");
            put("AZ", "5.6%");
            put("AR", "6.5%");
            put("CA", "7.25%");
            put("CO", "2.9%");
            put("CT", "6.35%");
            put("DE", "0%");
            put("FL", "6%");
            put("GA", "4%");
            put("HI", "4.166%");
            put("ID", "6%");
            put("IL", "6.25%");
            put("IN", "7%");
            put("IA", "6%");
            put("KS", "6.5%");
            put("KY", "6%");
            put("LA", "4.45%");
            put("ME", "5.5%");
            put("MD", "6%");
            put("MA", "6.25%");
            put("MI", "6%");
            put("MN", "6.875%");
            put("MS", "7%");
            put("MO", "4.225%");
            put("MT", "0%");
            put("NE", "5.5%");
            put("NV", "6.85%");
            put("NH", "0%");
            put("NJ", "6.625%");
            put("NM", "5.125%");
            put("NY", "4%");
            put("NC", "4.75%");
            put("ND", "5%");
            put("OH", "5.75%");
            put("OK", "4.5%");
            put("OR", "0%");
            put("PA", "6%");
            put("RI", "7%");
            put("SC", "6%");
            put("SD", "4%");
            put("TN", "7%");
            put("TX", "6.25%");
            put("UT", "6.1%");
            put("VT", "6%");
            put("VA", "5.3%");
            put("WA", "6.5%");
            put("WV", "6%");
            put("WI", "5%");
            put("WY", "4%");
        }};
}
