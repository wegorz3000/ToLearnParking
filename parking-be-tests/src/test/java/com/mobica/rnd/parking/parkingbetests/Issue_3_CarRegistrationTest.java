package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.TestSuiteData;
import com.mobica.rnd.parking.parkingbetests.support.RestAssuredProcessor;
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
public class Issue_3_CarRegistrationTest {

    private static final String ISSUE_NAME = "3-CarRegistration";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;

    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
    }

    @Test
    @Ignore
    public void car_registration_success_test() {
        suiteData.createTestCaseExecutor("car_registration_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_boundary_values_success_test() {
        suiteData.createTestCaseExecutor("car_registration_with_boundary_values_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_without_brand_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_without_brand_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_too_long_brand_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_too_long_brand_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_without_model_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_without_model_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_too_long_model_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_too_long_model_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_without_color_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_too_long_model_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_too_long_color_name_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_too_long_color_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_without_plate_number_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_without_plate_number_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_too_long_plate_number_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_too_long_plate_number_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_incorrect_active_state_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_incorrect_active_state_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void car_registration_with_incorrect_boundary_values_failure_test() {
        suiteData.createTestCaseExecutor("car_registration_with_incorrect_boundary_values_failure")
                .performTests(processor, suiteData);
    }

}
