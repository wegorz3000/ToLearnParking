package com.mobica.rnd.parking.parkingbetests.support;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by int_eaja on 2017-08-01.
 */
public class TestCaseData {

    protected JSONObject jsonObject;
    private TestSuiteData suiteData;

    private List<Map<String, JSONObject[]>> requestConfigurationItemResponsesMapList;
    private SuiteConfigurationSourceDataResolver suiteConfDataResolver;
    private ItemConfigurationSourceDataResolver itemConfDataResolver;

    {
        requestConfigurationItemResponsesMapList = new ArrayList<>();
        suiteConfDataResolver = new SuiteConfigurationSourceDataResolver();
        itemConfDataResolver = new ItemConfigurationSourceDataResolver();
    }

    public TestCaseData(TestSuiteData suiteData, JSONObject testCaseObj) {
        this.suiteData = suiteData;
        this.jsonObject = testCaseObj;
    }

    public String getBaseURL() {
        return suiteData.getBaseURL();
    }

    public JSONObject[] getItemConfigurationRequests(String key) {
        JSONArray inputs = (JSONArray) jsonObject.get("data");
        JSONObject[] res = new JSONObject[inputs.size()];
        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject testCaseData = (JSONObject) next;
                JSONObject itemConfObj = (JSONObject) testCaseData.get("item_configuration_requests");
                JSONObject itemConfEl = (JSONObject) itemConfObj.get(key);
                res[i] = itemConfEl;
            }
            i++;
        }
        return res;

    }

    ////
    // extend start


    public void addConfigurationItemResponses(String key, JSONObject[] responses, int index) {
        Map<String, JSONObject[]> iresps = null;
        if (requestConfigurationItemResponsesMapList.size() > index) {
            iresps = requestConfigurationItemResponsesMapList.get(index);
        }
        if (iresps == null) {
            iresps = new HashMap<>();
            requestConfigurationItemResponsesMapList.add(index, iresps);
        }
        iresps.put(key, responses);
    }

    public TestCaseExecutor createExecutor() {
        return new TestCaseExecutor(this);
    }

    ///
    // conf items

    public void extendConfigurationItemRequestQueryParam(SourceDataType resolverType, String queryParamKey, String[] assignmentData, int index) {
        ISourceDataResolver sourceDataResolver = getSourceDataResolver(resolverType);
        extendConfigurationItemRequestQueryParam(sourceDataResolver, queryParamKey, assignmentData, index);
    }

    private void extendConfigurationItemRequestQueryParam(ISourceDataResolver sourceDataResolver, String queryParamKey, String[] assignmentData, int index) {
        String sourceConfKey = assignmentData[0];
        String sourceConfDataKey = assignmentData[1];
        String targetConfItemKey = assignmentData[2];

        JSONArray inputs = (JSONArray) jsonObject.get("data");

        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject testCaseDataItem = (JSONObject) next;
                JSONObject targetConfItem = getItemConfigurationRequest(testCaseDataItem, targetConfItemKey);

                JSONArray datas = (JSONArray) targetConfItem.get("data");
                ListIterator datasIt = datas.listIterator();
                while (datasIt.hasNext()) {
                    Object dnext = datasIt.next();
                    JSONObject dnextObj = (JSONObject) dnext;

                    JSONObject queryParams = (JSONObject) dnextObj.get("query_params");

                    JSONObject[] responses = sourceDataResolver.getData(i, sourceConfKey);
                    if (responses.length > index) {
                        JSONObject resp = responses[index];
                        Object value = JSONUtils.getJSONValueByComplexKey(resp, sourceConfDataKey);
                        queryParams.put(queryParamKey, value);
                    }

                }


            }
            i++;
        }

    }

    public void extendConfigurationItemRequestBody(DataAssociationType assoType, SourceDataType resolverType, String targetKey, String[] assignmentData, int[] indexesData) {
        ISourceDataResolver sourceDataResolver = getSourceDataResolver(resolverType);
        extendConfigurationItemRequestBody(assoType, sourceDataResolver, targetKey, assignmentData, indexesData);
    }

    public void extendConfigurationItemRequestBody(DataAssociationType assoType, ISourceDataResolver sourceDataResolver, String targetKey, String[] assignmentData, int[] indexesData) {
        // conf item to conf item
        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];
        String targetDataKey = assignmentData[2];

        int sourceIndex = indexesData[0];
        int targetIndex = indexesData[1];

        JSONArray inputs = (JSONArray) jsonObject.get("data");

        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject dataItemObj = (JSONObject) next;
                JSONObject[] sourceResponses = sourceDataResolver.getData(i, sourceKey);

                JSONArray targetRequests = getItemConfigurationRequestData(dataItemObj, targetKey);
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
            i++;
        }

    }

    private JSONArray getItemConfigurationRequestData(JSONObject dataItemObj, String key) {
        JSONObject rData = (JSONObject) dataItemObj.get("item_configuration_requests");
        JSONObject obj = (JSONObject) rData.get(key);
        return (JSONArray) obj.get("data");
    }

    private JSONObject getItemConfigurationRequest(JSONObject dataItemObj, String key) {
        JSONObject rData = (JSONObject) dataItemObj.get("item_configuration_requests");
        JSONObject obj = (JSONObject) rData.get(key);
        return obj;
    }

    public void extendTestCaseRequestPathParam(SourceDataType resolverType, String pathParamKey, String[] assignmentData, int index) {
        ISourceDataResolver resolver = getSourceDataResolver(resolverType);
        extendTestCaseRequestPathParam(resolver, pathParamKey, assignmentData, index);
    }


    public void extendTestCaseRequestPathParam(ISourceDataResolver sourceDataResolver, String pathParamKey, String[] assignmentData, int index) {

        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];

        JSONArray inputs = (JSONArray) jsonObject.get("data");
        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject testCaseDataItem = (JSONObject) next;
                JSONObject testCaseDataItemRequest = (JSONObject) testCaseDataItem.get("request");
                JSONObject pathParams = (JSONObject) testCaseDataItemRequest.get("path_params");

                JSONObject[] responses = sourceDataResolver.getData(i, sourceKey);
                if (responses.length > index) {
                    JSONObject resp = responses[index];
                    Object value = JSONUtils.getJSONValueByComplexKey(resp, sourceDataKey);

                    pathParams.put(pathParamKey, value);
                }

            }
            i++;
        }
    }

    ///

    public void extendTestCaseRequestQueryParam(SourceDataType resolverType, String queryParamKey, String[] assignmentData, int index) {
        ISourceDataResolver sourceDataResolver = getSourceDataResolver(resolverType);
        extendTestCaseRequestQueryParam(sourceDataResolver, queryParamKey, assignmentData, index);
    }

    public void extendTestCaseRequestQueryParam(ISourceDataResolver sourceDataResolver, String queryParamKey, String[] assignmentData, int index) {

        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];

        JSONArray inputs = (JSONArray) jsonObject.get("data");
        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                JSONObject testCaseDataItem = (JSONObject) next;
                JSONObject testCaseDataItemRequest = (JSONObject) testCaseDataItem.get("request");
                JSONObject queryParams = (JSONObject) testCaseDataItemRequest.get("query_params");

                JSONObject[] responses = sourceDataResolver.getData(i, sourceKey);
                if (responses.length > index) {
                    JSONObject resp = responses[index];
                    Object value = JSONUtils.getJSONValueByComplexKey(resp, sourceDataKey);

                    queryParams.put(queryParamKey, value);
                }

            }
            i++;
        }
    }

    public void extendTestCaseBody(DataAssociationType assoType, CommunicationType commType, SourceDataType resolverType, String[] assignmentData, int[] indexesData) {
        ISourceDataResolver sourceDataResolver = getSourceDataResolver(resolverType);
        extendTestCaseBody(assoType, commType, sourceDataResolver, assignmentData, indexesData);

    }

    public void extendTestCaseBody(DataAssociationType assoType, CommunicationType commType, ISourceDataResolver sourceDataResolver, String[] assignmentData, int[] indexesData) {

        // fill test case item response/request
        String sourceKey = assignmentData[0];
        String sourceDataKey = assignmentData[1];
        String targetDataKey = assignmentData[2];

        int sourceIndex = indexesData[0];
        int targetIndex = indexesData[1];

        JSONArray inputs = (JSONArray) jsonObject.get("data");
        int i = 0;
        ListIterator it = inputs.listIterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next instanceof JSONObject) {
                // test case item data
                JSONObject testCaseItem = (JSONObject) next;
                String commKey = (commType == CommunicationType.REQUEST ? "request" : "response");
                JSONObject tcComm = (JSONObject) testCaseItem.get(commKey); // "response"

                JSONObject[] sourceResponses = sourceDataResolver.getData(i, sourceKey);

                Object tcCommBody = tcComm.get("body");
                if (tcCommBody instanceof JSONArray) {
                    JSONArray tcCommBodyArray = (JSONArray) tcCommBody;
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
                } else if (tcCommBody instanceof JSONObject) {
                    JSONUtils.extendTarget(sourceResponses, (JSONObject) tcCommBody, sourceDataKey, targetDataKey);
                }
            }
            i++;
        }
    }


    private ISourceDataResolver getSourceDataResolver(SourceDataType type) {
        if (type == SourceDataType.SUITE) {
            return suiteConfDataResolver;
        } else {
            return itemConfDataResolver;
        }
    }

    public enum CommunicationType {
        REQUEST, RESPONSE
    }

    public interface ISourceDataResolver {
        JSONObject[] getData(int i, String key);
    }

    public class ItemConfigurationSourceDataResolver implements ISourceDataResolver {
        @Override
        public JSONObject[] getData(int i, String key) {
            Map<String, JSONObject[]> ircResponses = requestConfigurationItemResponsesMapList.get(i);
            JSONObject[] sourceResponses = ircResponses.get(key);
            return sourceResponses;
        }
    }

    public class SuiteConfigurationSourceDataResolver implements ISourceDataResolver {
        @Override
        public JSONObject[] getData(int i, String key) {
            return suiteData.getConfigurationResponses(key);
        }
    }

}
