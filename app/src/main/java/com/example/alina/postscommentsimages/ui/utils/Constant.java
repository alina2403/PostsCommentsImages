package com.example.alina.postscommentsimages.ui.utils;

import java.util.Collections;
import java.util.List;

public class Constant {
    public static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
    public static final String WEATHER_URL = "https://samples.openweathermap.org/";

    public static <T> List<T> selectTenElements(List<T> list) {
        Collections.shuffle(list);
        return list.subList(0, list.size() > 10 ? 10 : list.size());
    }
}