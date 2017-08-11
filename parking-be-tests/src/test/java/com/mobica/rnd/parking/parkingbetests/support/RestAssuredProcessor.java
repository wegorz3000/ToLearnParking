package com.mobica.rnd.parking.parkingbetests.support;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.CoreMatchers;
import org.hamcrest.text.MatchesPattern;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by int_eaja on 2017-07-25.
 */
@Component
public class RestAssuredProcessor {

    @Value("${endpoint.base.url}")
    private String baseURL;

    public void reset() {
        RestAssured.reset();
    }

    private RequestSpecification getRequestSpecification(JSONObject data) {

        String method = data.get("method").toString();
        Object body = data.get("body");
        String bodyStr = (body != null ? body.toString() : "");
        String contentType = (String) data.get("Content-Type");

        RequestSpecification spec = RestAssured
                .given()
                .contentType(contentType)
                .body(bodyStr)
                .when();
        return spec;

    }

    public String getBaseURL() {
        return baseURL;
    }

    private ValidatableResponse checkStatusCode(ValidatableResponse vr, JSONObject response) {
        vr.statusCode(Integer.parseInt(response.get("code").toString()));
        return vr;
    }

    private ValidatableResponse performTestRequestByMethod(TestSuiteData data, JSONObject request) {

        ValidatableResponse resp = null;
        String body = data.getBodyContent(request);
        System.out.println("body: ");
        System.out.println(body);
        Map<String, String> queryParams = data.getQueryParams(request);
        Map<String, String> pathParams = data.getPathParams(request);

        switch (data.getMethod()) {
            case "POST":
                resp =
                        RestAssured
                                .given()
                                .queryParams(queryParams)
                                .pathParams(pathParams)
                                .contentType(data.getContentType())
                                .body(body)
                                .when()
                                .post(data.getUrl())
                                .then();
                break;
            case "GET":
                resp =
                        RestAssured
                                .given()
                                .queryParams(queryParams)
                                .pathParams(pathParams)
                                .contentType(data.getContentType())
                                .body(body)
                                .when()
                                .get(data.getUrl())
                                .then();
                break;
            case "PUT":
                resp =
                        RestAssured
                                .given()
                                .queryParams(queryParams)
                                .pathParams(pathParams)
                                .contentType(data.getContentType())
                                .body(body)
                                .when()
                                .put(data.getUrl())
                                .then();
                break;
            case "DELETE":
                resp =
                        RestAssured
                                .given()
                                .queryParams(queryParams)
                                .pathParams(pathParams)
                                .contentType(data.getContentType())
                                .body(body)
                                .when()
                                .delete(data.getUrl())
                                .then();
                break;
            case "PATCH":
                resp =
                        RestAssured
                                .given()
                                .queryParams(queryParams)
                                .pathParams(pathParams)
                                .contentType(data.getContentType())
                                .body(body)
                                .when()
                                .patch(data.getUrl())
                                .then();
                break;
            default:
                break;
        }
        return resp;
    }

    private void checkPropertyValues(ValidatableResponse vr, Object data, String key) {
        if (data instanceof JSONArray) {
            JSONArray arr = (JSONArray) data;
            { // check array size
                int arrSize = arr.size();
                String sizeKey = (key.length() == 0 ? "size()" : key + ".size()");
                vr.body(sizeKey, CoreMatchers.is(arrSize));
            }
            ListIterator it = arr.listIterator();
            int i = 0;
            while (it.hasNext()) {
                Object obj = it.next();
                String keyi = key + "[" + i + "]";
                checkPropertyValues(vr, obj, keyi);
                i++;
            }
        } else if (data instanceof JSONObject) {
            JSONObject obj = (JSONObject) data;
            for (Object objk : obj.keySet()) {
                Object value = obj.get(objk);
                String kk = objk.toString();
                if (kk.indexOf(" ") > 0) {
                    kk = "\"" + kk + "\"";
                }
                String keyi = key + (key.length() == 0 ? kk : "." + kk);
                checkPropertyValues(vr, value, keyi);
            }
        } else {
            checkSimpleValue(vr, data, key);
        }
    }

    private void checkSimpleValue(ValidatableResponse vr, Object data, String key) {

        if (data instanceof String) {
            String escapedDataStr = (String) data;// Pattern.quote((String) data);
            //TODO, czy jak bedzie zwykle equal to , to trzeba bedzie escape
            vr.body(key, MatchesPattern.matchesPattern(escapedDataStr));
        } else if (data instanceof Boolean ||//
                data instanceof Integer ||//
                data instanceof Float ||//
                data instanceof Double) {
            vr.body(key, CoreMatchers.equalTo(data));
        } else if (data == null) {
            // TODO - not supported
            vr.body(key, CoreMatchers.is(CoreMatchers.nullValue()));
        }
    }

    public void performTests(TestSuiteData suiteData, TestCaseData jsonTestCaseData) {
        SoftAssertions softAssertions = new SoftAssertions();
        JSONObject testObj = jsonTestCaseData.jsonObject;//getTestDatas();
        JSONArray inputs = (JSONArray) testObj.get("data");
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof JSONObject) {
                JSONObject item = (JSONObject) obj;
                JSONObject request = (JSONObject) item.get("request");

                JSONObject response = (JSONObject) item.get("response");
                Object bodyRespObj = response.get("body");

                ValidatableResponse vr = performTestRequestByMethod(suiteData, request);

                if (vr == null) {
                    // TODO
                    return;
                }

                vr.log().ifValidationFails();
                vr.log().everything();


                try {
                    checkPropertyValues(vr, bodyRespObj, "");
                } catch (Throwable t) {
                    String desc = item.get("description") == null ? "" : item.get("description").toString();
                    softAssertions.fail(composeErrorLog(desc, vr.extract(), t), t);
                }
                try {
                    checkStatusCode(vr, response);
                } catch (Throwable t) {
                    String desc = item.get("description") == null ? "" : item.get("description").toString();
                    softAssertions.fail(composeErrorLog(desc, vr.extract(), t), t);
                }
            }
        }
        softAssertions.assertAll();

    }

    private String composeErrorLog(String description, ExtractableResponse<Response> er, Throwable t) {
        List<String> list = new ArrayList<>();
        list.add(description);
        list.add(er.statusLine());
        for (Header h : er.headers().asList()) {
            list.add(h.toString());
        }
        list.add(er.response().asString());
        list.add(t.getMessage());
        // prettyprint
        return list.stream().collect(Collectors.joining("\n"));
    }
}
