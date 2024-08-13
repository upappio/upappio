package com.upapp.w2a;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class SharedPrefUtil {
    private Context shareContext;
    private SharedPreferences mySharedPref;

    public SharedPrefUtil(Context appContext) {
        shareContext = appContext;
        mySharedPref = shareContext.getSharedPreferences("onLine", Context.MODE_PRIVATE);
    }
    public void clear() {
        try {
            mySharedPref.edit().clear().apply();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public void setSharedPref(String key, String value) {
        try {
            mySharedPref.edit().putString(key, value).apply();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public void setSharedPref(String key, int value) {
        try {
            mySharedPref.edit().putInt(key, value).apply();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public void setSharedPref(String key, boolean value) {
        try {
            mySharedPref.edit().putBoolean(key, value).apply();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public void setSharedPref(String key, float value) {
        try {
            mySharedPref.edit().putFloat(key, value).apply();
        } catch (Exception e) {
            System.out.println("setSharedPref ERROR: " + e.getMessage());
        }
    }

    public String getValue(String key) {
        String value = "";
        try {
            value = mySharedPref.getString(key, null);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    public String getValue(String key, String defValue) {
        String value = "";
        try {
            value = mySharedPref.getString(key, defValue);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    public int getIntValue(String key) {
        int value = 0;
        try {
            value = mySharedPref.getInt(key, 0);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    public boolean getBolValue(String key) {
        boolean value = false;
        try {
            value = mySharedPref.getBoolean(key, false);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    public float getFloValue(String key) {
        float value = 0;
        try {
            value = mySharedPref.getFloat(key, 0);
        } catch (Exception e) {
            System.out.println("getSharedPref ERROR: " + e.getMessage());
        }
        return value;
    }

    @SuppressLint("CommitPrefEdits")
    public void delValue(String key) {
        mySharedPref.edit().remove(key);
    }


    public void putBean(String key, Object obj) {
        if (obj instanceof Serializable) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                String string64 = new String(Base64.encode(baos.toByteArray(), 0));
                mySharedPref.edit().putString(key, string64).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            throw new IllegalArgumentException("the obj must implement Serializable");
        }

    }

    public Object getBean(String key) {
        Object obj = null;
        try {
            String base64 = mySharedPref.getString(key, "");
            if (base64.equals("")) {
                return null;
            }
            byte[] base64Bytes = Base64.decode(base64.getBytes(), 1);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
