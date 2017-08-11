package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.JSONTestCaseDataProvider;
import org.json.simple.JSONObject;

import java.io.File;

/**
 * Created by int_eaja on 2017-08-08.
 */
public class BaseRequestsData {

    private static BaseRequestsData instance;
    private final String NAME = "BasicRequests";
    private JSONObject jsonData;

    private BaseRequestsData() {
        this.jsonData = JSONTestCaseDataProvider.get().getData("test_data" + File.separator + NAME + ".json");
    }

    public static BaseRequestsData get() {
        if (instance == null) {
            synchronized (BaseRequestsData.class) {
                if (instance == null) {
                    instance = new BaseRequestsData();
                }
            }
        }
        return instance;
    }

    public JSONObject getRequestData(String name) {
        Object res = jsonData.get(name);
        if (res instanceof JSONObject) {
            return (JSONObject) res;
        }
        return null;
    }


}
