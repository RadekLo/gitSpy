package com.task.gitSpy.Holiday.model;

import static com.task.gitSpy.Holiday.model.HolidayQueryParams.APIParameter.*;

import java.util.HashMap;

import com.task.gitSpy.model.QueryParams;

public class HolidayQueryParams extends QueryParams{

    public HolidayQueryParams key(String key) {
        params.put(API_KEY.toString(), key);
        return this;
    }
    
    public HolidayQueryParams country(String country) {
        params.put(COUNTRY.toString(), country);
        return this;
    }

    public HolidayQueryParams year(int year) {
        params.put(YEAR.toString(), year);
        return this;
    }
   
    public Object getKey() {
        return params.get(API_KEY.toString());
    }
    
    public Object getCountry() {
        return params.get(COUNTRY.toString());
    }
    
    public Object getYear() {
        return params.get(YEAR.toString());
    }
    
    @Override
    public String toString() {
        return queryString();
    }

    /**
     * Enumeration of allowed query parameters
     */
    public enum APIParameter {
    	API_KEY("key"),
        COUNTRY("country"),
        YEAR("year"),
        PUBLIC("public"),
        FORMAT("format"),
        PRETTY("pretty");

        private String param;

        APIParameter(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return param;
        }
    }
}
