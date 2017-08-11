package com.mobica.rnd.parking.parkingbetests.support.extractors;

import com.mobica.rnd.parking.parkingbetests.BaseRequestsData;
import com.mobica.rnd.parking.parkingbetests.support.JSONTestCaseDataProvider;
import com.mobica.rnd.parking.parkingbetests.support.JSONUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by int_eaja on 2017-08-08.
 */
//@Component
public class RESTExtractor implements IDataExtractor {

    private BaseRequestsData baseRequestsData;

    private String baseURL;

    public RESTExtractor(String baseURL) {
        this.baseURL = baseURL;
        this.baseRequestsData = BaseRequestsData.get();
    }

    @Override
    public JSONObject[] extractData(JSONObject obj, String key) {
        JSONObject[] responses = extractJSONResponses(obj);
        return responses;
    }


    private JSONObject getResponse(JSONObject data) throws IOException {
        // TODO - dolozyc wypelnianie path param
        String urlStr = null;
        String method = null;
        String contentType = null;
        Object requestNameObj = data.get("request_name");
        if (requestNameObj instanceof String && //
                baseRequestsData.getRequestData((String) requestNameObj) instanceof JSONObject) {
            JSONObject datax = baseRequestsData.getRequestData((String) requestNameObj);
            urlStr = (String) datax.get("url");
            method = (String) datax.get("method");
            contentType = (String) datax.get("Content-Type");
        } else {
            urlStr = (String) data.get("url");
            method = (String) data.get("method");
            contentType = (String) data.get("Content-Type");
        }

        method = method.toUpperCase();

        Object body = data.get("body");
        Object queryParams = data.get("query_params");
        String bodyStr = (body != null ? body.toString() : "");


        if (queryParams instanceof Map<?, ?>) {
            Map<?, ?> qpMap = (Map<?, ?>) queryParams;
            if (qpMap.size() > 0) {
                StringBuilder b = new StringBuilder();
                b.append("?");
                for (Object qp : qpMap.keySet()) {
                    b.append((qp.toString() + "=" + qpMap.get(qp).toString() + "&"));
                }
                b.setLength(b.length() - "&".length());
                urlStr += b.toString();

            }
        }

        URL url = new URL(baseURL + urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Length", Integer.toString(bodyStr.length()));


        connection.setRequestMethod(method);
        connection.setRequestProperty("Content-Type", contentType);

        OutputStream os = connection.getOutputStream();
        os.write(bodyStr.getBytes());
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));

        String output;
        String outputX = "";
        while ((output = br.readLine()) != null) {
            outputX += output;
        }

        connection.disconnect();
        return JSONTestCaseDataProvider.get().getJSONObject(outputX);

    }

    public JSONObject[] extractJSONResponses(JSONObject obj) {
        Object arrObj = obj.get("data");
        if (!(arrObj instanceof JSONArray)) {
            return null;
        }
        JSONArray datas = (JSONArray) arrObj;
        JSONObject[] responses = new JSONObject[datas.size()];

        int i = 0;
        ListIterator it = datas.listIterator();
        JSONObject mainRequestObj = JSONUtils.getShallowCopy(obj);
        mainRequestObj.remove("data");
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject datai = (JSONObject) next;
                JSONObject requesti = JSONUtils.getExtendedJSONObject(mainRequestObj, datai);
                try {
                    JSONObject ji = getResponse(requesti);
                    responses[i] = ji;
                } catch (IOException e) {
                    responses[i] = null;
                    e.printStackTrace();
                }

            } else {
                responses[i] = null;
            }
            i++;
        }
        return responses;

    }
}