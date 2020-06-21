package com.santosh.springwebsocket.util;

import java.util.HashMap;
import java.util.Map;

public class HttpUtil {

    public static Map<String, String> getQueryMap(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();

        for (String param : params) {
            String[] keyPair = param.split("=");

            String name = keyPair[0];
            String value = keyPair[1];
            map.put(name, value);
        }
        return map;
    }
}
