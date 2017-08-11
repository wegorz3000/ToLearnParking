package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.*;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * Created by int_eaja on 2017-08-10.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_20_ChangeCarActivityFlagTest {

    private static final String ISSUE_NAME = "20-ChangeCarActivityFlag";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;


    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
    }

    @Test
    public void change_activity_state_flag_success_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_success")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestPathParam(SourceDataType.ITEM, "id", new String[]{"cars", "id"}, 0)
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void change_activity_state_flag_not_success_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_success")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestPathParam(SourceDataType.ITEM, "id", new String[]{"cars", "id"}, 0)
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void change_activity_state_flag_using_incorrect_active_state_values_failure_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_using_incorrect_active_state_values_failure")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestPathParam(SourceDataType.ITEM, "id", new String[]{"cars", "id"}, 0)
                .performTests(processor, suiteData);
    }

    @Test
    public void change_activity_state_flag_without_active_state_values_failure_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_without_active_state_values_failure")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestPathParam(SourceDataType.ITEM, "id", new String[]{"cars", "id"}, 0)
                .performTests(processor, suiteData);
    }

    @Test
    public void change_activity_state_flag_using_incorrect_car_value_failure_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_using_incorrect_car_value_failure")
                .performTests(processor, suiteData);
    }

    @Test
    @Ignore
    public void change_activity_state_flag_without_providing_car_value_failure_test() {
        suiteData
                .createTestCaseExecutor("change_activity_state_flag_without_providing_car_value_failure")
                .performTests(processor, suiteData);
    }

}
