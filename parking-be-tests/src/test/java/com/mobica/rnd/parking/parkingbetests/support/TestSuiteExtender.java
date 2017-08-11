package com.mobica.rnd.parking.parkingbetests.support;

import com.mobica.rnd.parking.parkingbetests.support.extractors.IDataExtractor;
import com.mobica.rnd.parking.parkingbetests.support.extractors.RESTExtractor;
import org.json.simple.JSONObject;

/**
 * Created by int_eaja on 2017-08-04.
 */
public class TestSuiteExtender {

    private TestSuiteData suiteData;
    private RESTExtractor extractor;

    // TODO - dorobic mozliwosc korzystania z dowolnego extractora (tak jak dla test casow)

    public TestSuiteExtender(TestSuiteData suiteData) {

        this.suiteData = suiteData;
        this.extractor = new RESTExtractor(suiteData.getBaseURL());
    }


    public TestSuiteExtender extractSuiteConfigurationJSONResponses(IDataExtractor extractor, String key) {
        JSONObject obj = suiteData.getConfigurationRequest(key);
        JSONObject[] responses = extractor.extractData(obj, key);
        suiteData.addConfigurationResponses(key, responses);
        return this;
    }

    public TestSuiteExtender extractSuiteConfigurationJSONResponses(String key) {
        JSONObject obj = suiteData.getConfigurationRequest(key);
        JSONObject[] responses = extractor.extractData(obj, key);
        suiteData.addConfigurationResponses(key, responses);
        return this;
    }

    public TestSuiteExtender extendSuiteConfigurationRequestQueryParam(String queryParamKey, String[] assignmentData, int index) {
        suiteData.extendSuiteConfigurationQueryParam(queryParamKey, assignmentData, index);
        return this;
    }

    public TestSuiteExtender extendSuiteConfigurationRequestBody(DataAssociationType assoType, String targetKey, String[] assignmentData, int[] indexesData) {
        suiteData.extendSuiteConfigurationRequestBody(assoType, targetKey, assignmentData, indexesData);
        return this;
    }
}
