package com.mobica.rnd.parking.parkingbetests.support.extractors;

import org.json.simple.JSONObject;

/**
 * Created by int_eaja on 2017-08-08.
 */
public interface IDataExtractor {

    JSONObject[] extractData(JSONObject obj, String key);
}
