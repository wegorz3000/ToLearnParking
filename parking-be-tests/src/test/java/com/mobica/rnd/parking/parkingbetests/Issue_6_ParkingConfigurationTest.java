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
public class Issue_6_ParkingConfigurationTest {

    private static final String ISSUE_NAME = "6-ParkingConfiguration";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;

    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
    }

    @Test
    public void parking_configuration_success_test() {
        suiteData.createTestCaseExecutor("parking_configuration_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_correct_boundary_values_success_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_correct_boundary_values_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_without_capacity_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_without_capacity_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_without_name_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_without_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_capacity_not_as_a_number_value_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_capacity_not_as_a_number_value_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_capacity_too_long_length_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_capacity_too_long_length_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_capacity_too_long_not_numerical_length_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_capacity_too_long_not_numerical_length_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_name_too_long_length_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_name_too_long_length_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void parking_configuration_with_incorrect_boundary_values_failure_test() {
        suiteData.createTestCaseExecutor("parking_configuration_with_incorrect_boundary_values_failure")
                .performTests(processor, suiteData);
    }

}
