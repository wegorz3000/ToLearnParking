package com.mobica.rnd.parking.parkingbetests.support;


import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;

import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by int_eaja on 2017-07-25.
 */
public class JSONTestCaseDataProvider {

    private static JSONTestCaseDataProvider instance;

    public static JSONTestCaseDataProvider get() {
        if (instance == null) {
            synchronized (JSONTestCaseDataProvider.class) {
                if (instance == null) {
                    instance = new JSONTestCaseDataProvider();
                }
            }
        }
        return instance;
    }

    private String getFile(String fileName) {

        String result = "";

        ClassLoader classLoader = getClass().getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    public JSONObject getJSONObject(String str) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(str);
            JSONObject jobj = (JSONObject) obj;
            return jobj;

        } catch (ParseException pe) {

            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
        return null;
    }

    public JSONObject getData(String fileName) {
        String s = getFile(fileName);
        return getJSONObject(s);
    }

}
