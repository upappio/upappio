package com.io.upapp.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Iterator;

public class ToolUtils {

    public static String beanToString(Object bean) {
        StringBuilder builder = new StringBuilder();
        Class<?> clazz = bean.getClass();

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                String key = field.getName();
                Object value = field.get(bean);
                if (value!=null){
                    builder.append(key).append(": ").append(value).append("\n");
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

    public static String jsonStringToMap(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);

            // 提取数据
            StringBuilder builder = new StringBuilder();
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String key = it.next();
                String value = jsonObject.getString(key);
                builder.append(key).append(": ").append(value).append("\n");
            }

            return builder.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
