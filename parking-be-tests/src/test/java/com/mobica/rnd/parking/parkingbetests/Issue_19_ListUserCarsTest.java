package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * Created by int_eaja on 2017-08-01.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_19_ListUserCarsTest {

    private static final String ISSUE_NAME = "19-ListUserCars";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;

    @PostConstruct
    public void setUp() {

        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
    }

    @Test
    public void list_user_cars_filled_success_test() {
        suiteData
                .createTestCaseExecutor("list_user_cars_filled_success")
                .extractItemConfigurationJSONResponses("user")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "cars", new String[]{"user", "userId", "owner.userId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "id", new String[]{"user", "userId"}, 0)
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"user", "userId", "owner.userId"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);


    }

    @Test
    public void list_user_cars_empty_success_test() {
        suiteData
                .createTestCaseExecutor("list_user_cars_empty_success")
                .extractItemConfigurationJSONResponses("user")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "id", new String[]{"user", "userId"}, 0)
                .performTests(processor, suiteData);

    }
}
