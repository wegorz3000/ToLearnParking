package com.mobica.rnd.parking.parkingbetests.support;

import com.mobica.rnd.parking.parkingbetests.support.extractors.IDataExtractor;
import com.mobica.rnd.parking.parkingbetests.support.extractors.RESTExtractor;
import org.json.simple.JSONObject;

/**
 * Created by int_eaja on 2017-08-03.
 */
public class TestCaseExecutor {

    private TestCaseData tcDatas;
    private RESTExtractor restExtractor;

    public TestCaseExecutor(TestCaseData jsonTestCaseData) {
        this.tcDatas = jsonTestCaseData;
        this.restExtractor = new RESTExtractor(tcDatas.getBaseURL());
    }

    public TestCaseExecutor extractItemConfigurationJSONResponses(String key) {
        // TODO - to chyba tworzyc w konstruktorze, a nie za kazdym razem?
//        RESTExtractor extractor = new RESTExtractor(tcDatas.getBaseURL());
        return extractItemConfigurationJSONResponses(restExtractor, key);
    }

    public TestCaseExecutor extractItemConfigurationJSONResponses(IDataExtractor extractor, String key) {

        JSONObject[] objs = tcDatas.getItemConfigurationRequests(key); // for each data item
        int i = 0;
        for (JSONObject obj : objs) {
            JSONObject[] responses = extractor.extractData(obj, key);
            tcDatas.addConfigurationItemResponses(key, responses, i);
            i++;
        }
        return this;
    }

    public TestCaseExecutor extendConfigurationItemRequestQueryParam(SourceDataType resolverType, String queryParamKey, String[] assignmentData, int index) {
        tcDatas.extendConfigurationItemRequestQueryParam(resolverType, queryParamKey, assignmentData, index);
        return this;
    }

    public TestCaseExecutor extendConfigurationItemRequestBody(DataAssociationType assoType, SourceDataType
            resolverType, String targetKey, String[] assignmentData, int[] indexesData) {
        tcDatas.extendConfigurationItemRequestBody(assoType, resolverType, targetKey, assignmentData, indexesData);
        return this;
    }

    public TestCaseExecutor extendTestCaseRequestPathParam(SourceDataType resolverType, String pathParamKey, String[] assignmentData, int index) {
        tcDatas.extendTestCaseRequestPathParam(resolverType, pathParamKey, assignmentData, index);
        return this;
    }


    public TestCaseExecutor extendTestCaseRequestQueryParam(SourceDataType resolverType, String queryParamKey, String[] assignmentData, int index) {
        tcDatas.extendTestCaseRequestQueryParam(resolverType, queryParamKey, assignmentData, index);
        return this;
    }

    public TestCaseExecutor extendTestCaseRequestQueryParam(SourceDataType resolverType, String queryParamKey, String[] assignmentData) {
        return this.extendTestCaseRequestQueryParam(resolverType, queryParamKey, assignmentData, 0);
    }

    private TestCaseExecutor extendTestCaseBody(DataAssociationType assoType, TestCaseData.CommunicationType commType, SourceDataType resolverType, String[] assignmentData, int[] indexesData) {
        tcDatas.extendTestCaseBody(assoType, commType, resolverType, assignmentData, indexesData);
        return this;
    }

    public TestCaseExecutor extendTestCaseRequestBody(DataAssociationType assoType, SourceDataType resolverType, String[] assignmentData, int[] indexesData) {
        tcDatas.extendTestCaseBody(assoType, TestCaseData.CommunicationType.REQUEST, resolverType, assignmentData, indexesData);
        return this;
    }

    public TestCaseExecutor extendTestCaseResponseBody(DataAssociationType assoType, SourceDataType resolverType, String[] assignmentData, int[] indexesData) {
        tcDatas.extendTestCaseBody(assoType, TestCaseData.CommunicationType.RESPONSE, resolverType, assignmentData, indexesData);
        return this;
    }

    public void performTests(RestAssuredProcessor processor, TestSuiteData suiteData) {
        processor.performTests(suiteData, tcDatas);
    }


}
