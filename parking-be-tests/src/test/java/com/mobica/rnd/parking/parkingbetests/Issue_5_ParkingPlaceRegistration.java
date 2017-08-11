package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.*;
import org.json.simple.JSONObject;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * Created by int_eaja on 2017-07-26.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_5_ParkingPlaceRegistration {

    private static final String ISSUE_NAME = "5-ParkingPlaceRegistration";

    private JSONObject parkingConfJResponse;
    private TestSuiteData suiteData;

    @Autowired
    private RestAssuredProcessor processor;

    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
        suiteData.createExtender()
                .extractSuiteConfigurationJSONResponses("parking");
    }

    @Test
    public void parking_place_registration_success_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_success")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    @Ignore
    public void parking_place_registration_without_number_value_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_without_number_value_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    @Ignore
    public void parking_place_registration_with_too_big_number_value_failure_test() {
        // TODO - supply with number here

        suiteData
                .createTestCaseExecutor("parking_place_registration_with_too_big_number_value_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void parking_place_registration_with_too_long_level_name_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_with_too_long_level_name_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_place_registration_with_too_long_location_name_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_with_too_long_location_name_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void parking_place_registration_with_incorrect_max_car_weight_value_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_with_incorrect_max_car_weight_value_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void parking_place_registration_with_max_car_length_value_as_not_a_number_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_with_max_car_length_value_as_not_a_number_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void parking_place_registration_with_max_car_width_value_as_not_a_number_failure_test() {
        suiteData
                .createTestCaseExecutor("parking_place_registration_with_max_car_width_value_as_not_a_number_failure")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }


}
