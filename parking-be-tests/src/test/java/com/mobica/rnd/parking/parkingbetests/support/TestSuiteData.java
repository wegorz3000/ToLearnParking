package com.mobica.rnd.parking.parkingbetests.support;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.util.*;

/**
 * Created by int_eaja on 2017-08-02.
 */
public class TestSuiteData {

    private JSONObject jsonData;
    private String url;
    private String method;
    private String contentType;

    private String baseURL;

    private Map<String, JSONObject[]> requestConfigurationResponsesMap;

    {
        requestConfigurationResponsesMap = new HashMap<>();
    }

    public TestSuiteData(String baseURL, String issueName) {
        this.baseURL = baseURL;
        this.jsonData = JSONTestCaseDataProvider.get().getData("test_data" + File.separator + issueName + ".json");
        this.url = this.jsonData.get("url").toString();
        this.method = this.jsonData.get("method").toString();
        this.contentType = this.jsonData.get("Content-Type").toString();
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void addConfigurationResponses(String key, JSONObject[] responses) {
        requestConfigurationResponsesMap.put(key, responses);
    }

    public JSONObject[] getConfigurationResponses(String key) {
        return requestConfigurationResponsesMap.get(key);
    }

    public JSONObject getConfigurationRequest(String key) {
        Object crObj = jsonData.get("configuration_requests");
        if (crObj instanceof JSONObject) {
            Object data = ((JSONObject) crObj).get(key);
            if (data instanceof JSONObject) {
                return (JSONObject) data;
            }
        }
        return null;
    }

    private JSONArray getConfigurationRequestData(String key) {
        JSONObject rData = (JSONObject) jsonData.get("configuration_requests");
        JSONObject obj = (JSONObject) rData.get(key);
        return (JSONArray) obj.get("data");
    }


    public TestCaseExecutor createTestCaseExecutor(String testCase) {
        JSONObject tcObj = (JSONObject) jsonData.get(testCase);
        TestCaseData tcd = new TestCaseData(this, tcObj);
        return new TestCaseExecutor(tcd);
    }

    public TestSuiteExtender createExtender() {
        return new TestSuiteExtender(this);
    }

    public void performTests(RestAssuredProcessor processor, String testCase) {
        JSONObject tcObj = (JSONObject) jsonData.get(testCase);
        TestCaseData tcd = new TestCaseData(this, tcObj);
        new TestCaseExecutor(tcd).performTests(processor, this);
    }

    public JSONObject getJsonData() {
        return jsonData;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getContentType() {
        return contentType;
    }

    public String getBodyContent(JSONObject request) {
        Object bodyObj = request.get("body");
        return bodyObj != null ? bodyObj.toString() : "";
    }

    public Map<String, String> getQueryParams(JSONObject request) {
        Map<String, String> queryParams = new HashMap();
        Object queryParamsObj = request.get("query_params");
        if (queryParamsObj instanceof JSONObject) {
            JSONObject ppObj = (JSONObject) queryParamsObj;
            for (Object ppkey : ppObj.keySet()) {
                queryParams.put(ppkey.toString(), ppObj.get(ppkey).toString());
            }
        }
        return queryParams;
    }

    public Map<String, String> getPathParams(JSONObject request) {
        Map<String, String> pathParams = new HashMap();
        Object pathParamsObj = request.get("path_params");
        if (pathParamsObj instanceof JSONObject) {
            JSONObject ppObj = (JSONObject) pathParamsObj;
            for (Object ppkey : ppObj.keySet()) {
                pathParams.put(ppkey.toString(), ppObj.get(ppkey).toString());
            }
        }
        return pathParams;
    }

    //////////////////


    public void extendSuiteConfigurationRequestBody(DataAssociationType assoType, String targetKey, String[] assignmentData, int[] indexesData) {

        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];
        String targetDataKey = assignmentData[2];

        int sourceIndex = indexesData[0];
        int targetIndex = indexesData[1];

        JSONObject[] sourceResponses = requestConfigurationResponsesMap.get(sourceKey);

        JSONArray targetRequests = getConfigurationRequestData(targetKey);
        ListIterator trIt = targetRequests.listIterator();
        while (trIt.hasNext()) {
            Object trNext = trIt.next();
            if (trNext instanceof JSONObject) {
                JSONObject trNextObj = (JSONObject) trNext;
                Object trNextBody = trNextObj.get("body");
                if (trNextBody instanceof JSONArray) {
                    JSONArray tcCommBodyArray = (JSONArray) trNextBody;
                    switch (assoType) {
                        case ONE_TO_ALL:
                            JSONUtils.extendTargetArrayOneToAll(sourceResponses, tcCommBodyArray, sourceDataKey, targetDataKey);
                            break;
                        case ALL_TO_ALL:
                            JSONUtils.extendTargetArrayAllToAll(sourceResponses, tcCommBodyArray, sourceDataKey, targetDataKey);
                            break;
                        case ONE_TO_ONE:
                            JSONUtils.extendTargetArrayOneToOne(sourceResponses, tcCommBodyArray, sourceDataKey, targetDataKey, sourceIndex, targetIndex);
                            break;
                    }
                } else if (trNextBody instanceof JSONObject) {
                    JSONUtils.extendTarget(sourceResponses, (JSONObject) trNextBody, sourceDataKey, targetDataKey);
                }
            }

        }

    }

    public void extendSuiteConfigurationQueryParam(String queryParamKey, String[] assignmentData, int index) {
        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];
        String targetKey = assignmentData[2];

        JSONObject targetConfItem = getConfigurationRequest(targetKey);

        JSONArray datas = (JSONArray) targetConfItem.get("data");
        ListIterator datasIt = datas.listIterator();
        while (datasIt.hasNext()) {
            Object dnext = datasIt.next();
            JSONObject dnextObj = (JSONObject) dnext;

            JSONObject queryParams = (JSONObject) dnextObj.get("query_params");

            JSONObject[] responses = requestConfigurationResponsesMap.get(sourceKey);
            if (responses.length > index) {
                JSONObject resp = responses[index];
                Object value = JSONUtils.getJSONValueByComplexKey(resp, sourceDataKey);
                queryParams.put(queryParamKey, value);
            }

        }


    }


}
