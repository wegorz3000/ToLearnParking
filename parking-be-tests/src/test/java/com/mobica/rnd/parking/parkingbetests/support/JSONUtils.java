package com.mobica.rnd.parking.parkingbetests.support;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * Created by int_eaja on 2017-08-03.
 */
public class JSONUtils {

    public static JSONObject getExtendedJSONObject(JSONObject target, JSONObject add) {
        JSONObject copy = getShallowCopy(target);
        for (Object key : add.keySet()) {
            copy.put(key, add.get(key));
        }
        return copy;
    }

    public static JSONObject getShallowCopy(JSONObject o) {
        JSONObject res = new JSONObject();
        for (Object key : o.keySet()) {
            res.put(key, o.get(key));
        }
        return res;
    }

    public static JSONObject getJSONValueParentByComplexKey(JSONObject obj, String key) {
        JSONObject root = obj;
        String[] keysX = key.split("\\.");
        if (keysX.length <= 1) {
            return root;
        }
        String[] keys = Arrays.copyOf(keysX, keysX.length - 1);
        for (String k : keys) {
            JSONObject x = getJSONObject(root, k);
            root = x;
        }
        return root;
    }

    public static Object getJSONValueByComplexKey(JSONObject obj, String key) {
        JSONObject parent = getJSONValueParentByComplexKey(obj, key);
        String last = getLastKey(key);
        return parent.get(last);
    }

    public static String getLastKey(String complexKey) {
        String[] keysX = complexKey.split("\\.");
        String last = keysX[keysX.length - 1];
        return last;
    }

    private static JSONObject getJSONObject(JSONObject source, String key) {
        Object obj = source.get(key);
        if (obj instanceof JSONObject) {
            return (JSONObject) obj;
        }
        JSONObject res = new JSONObject();
        source.put(key, res);
        return res;
    }

    public static void extendTargetArrayOneToAll(JSONObject[] sourceResponses, JSONArray targetData, String sourceDataKey, String targetDataKey) {
        JSONObject firstSource = sourceResponses[0];
        Object value = JSONUtils.getJSONValueByComplexKey(firstSource, sourceDataKey);
        ListIterator it = targetData.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                setTargetValue((JSONObject) next, value, targetDataKey);
            }
        }
    }


    public static void extendTargetArrayAllToAll(JSONObject[] sourceResponses, JSONArray targetData, String sourceDataKey, String targetDataKey) {

        int i = 0;
        ListIterator it = targetData.listIterator();
        while (it.hasNext() && sourceResponses.length > i) {
            JSONObject sourceResp = sourceResponses[i];
            Object value = JSONUtils.getJSONValueByComplexKey(sourceResp, sourceDataKey);
            Object next = it.next();
            if (next instanceof JSONObject) {
                setTargetValue((JSONObject) next, value, targetDataKey);
            }
            i++;
        }
    }

    public static void extendTargetArrayOneToOne(JSONObject[] sourceResponses, JSONArray targetData, String sourceDataKey, String targetDataKey, int sourceIndex, int targetIndex) {

        if (sourceResponses.length > sourceIndex && targetData.size() > targetIndex) {
            JSONObject sourceResp = sourceResponses[sourceIndex];
            Object value = JSONUtils.getJSONValueByComplexKey(sourceResp, sourceDataKey);
            Object next = targetData.get(targetIndex);
            if (next instanceof JSONObject) {
                setTargetValue((JSONObject) next, value, targetDataKey);
            }
        }

    }

    public static void extendTarget(JSONObject[] sourceResponses, JSONObject targetData, String sourceDataKey, String targetDataKey) {
        JSONObject firstSource = sourceResponses[0];
        Object value = JSONUtils.getJSONValueByComplexKey(firstSource, sourceDataKey);
        setTargetValue(targetData, value, targetDataKey);
    }

    public static void setTargetValue(JSONObject next, Object value, String targetDataKey) {
        JSONObject nextObj = (JSONObject) next;

        JSONObject targetObj = JSONUtils.getJSONValueParentByComplexKey(nextObj, targetDataKey);
        String icrLastKey = JSONUtils.getLastKey(targetDataKey);
        targetObj.put(icrLastKey, value);
    }


}
