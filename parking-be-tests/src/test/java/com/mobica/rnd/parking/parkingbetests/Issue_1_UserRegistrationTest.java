package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.TestSuiteData;
import com.mobica.rnd.parking.parkingbetests.support.RestAssuredProcessor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

import static io.restassured.RestAssured.given;

/**
 * Created by int_eaja on 2017-07-24.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_1_UserRegistrationTest {

    private static final String ISSUE_NAME = "1-UserRegistration";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;


    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
    }

    /*** success ***/

    @Test
    public void user_registration_success_test() {
        suiteData.createTestCaseExecutor("user_registration_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_small_letters_success_test() {
        suiteData.createTestCaseExecutor("user_registration_small_letters_success")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_correct_boundary_data_values_success_test() {
        suiteData.createTestCaseExecutor("user_registration_with_correct_boundary_data_values_success")
                .performTests(processor, suiteData);
    }

    /*** failure ***/

    @Test
    public void user_registration_without_name_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_without_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_only_first_or_last_name_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_only_first_or_last_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_too_long_name_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_too_long_name_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_without_mobica_shortcut_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_without_mobica_shortcut_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_too_long_or_too_short_mobica_shortcut_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_too_long_or_too_short_mobica_shortcut_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_without_email_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_without_email_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_too_long_email_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_too_long_email_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_incorrect_email_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_incorrect_email_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_mixed_incorrect_data_values_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_mixed_incorrect_data_values_failure")
                .performTests(processor, suiteData);
    }

    @Test
    public void user_registration_with_incorrect_boundary_data_values_failure_test() {
        suiteData.createTestCaseExecutor("user_registration_with_incorrect_boundary_data_values_failure")
                .performTests(processor, suiteData);
    }
}
