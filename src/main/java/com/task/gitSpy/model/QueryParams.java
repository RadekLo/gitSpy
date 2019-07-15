package com.task.gitSpy.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
* Base Class for passing multiple parameter to URL, with method to build it.
*/
public class QueryParams {

    protected Map<String, Object> params;

    public QueryParams() {
        params = new HashMap<>();
    }
    public String queryString() {

        if (params.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
        builder.append("?");
        while (it.hasNext()) {
            Entry<String, Object> e = it.next();
            builder.append(e.getKey()).append("=").append(e.getValue());
            if (it.hasNext()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    @Override
    public String toString() {
        return queryString();
    }
}
