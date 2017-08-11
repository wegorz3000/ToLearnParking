package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.*;
import com.mobica.rnd.parking.parkingbetests.support.extractors.DatesExtractor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.PostConstruct;

/**
 * Created by int_eaja on 2017-08-09.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_2_BookingParkingPlace {

    private static final String ISSUE_NAME = "2-BookingParkingPlace";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;

    private DatesExtractor datesExtractor;


    @PostConstruct
    public void setUp() {
        datesExtractor = new DatesExtractor();
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
        suiteData.createExtender()
                .extractSuiteConfigurationJSONResponses(datesExtractor, "slots_dates")
                .extractSuiteConfigurationJSONResponses("parking")
                .extendSuiteConfigurationRequestBody(DataAssociationType.ONE_TO_ALL, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractSuiteConfigurationJSONResponses("parking_places")
                .extendSuiteConfigurationRequestQueryParam("start", new String[]{"slots_dates", "start", "parking_slots"}, 0)
                .extendSuiteConfigurationRequestQueryParam("end", new String[]{"slots_dates", "end", "parking_slots"}, 0)
                .extendSuiteConfigurationRequestBody(DataAssociationType.ALL_TO_ALL, "parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractSuiteConfigurationJSONResponses("parking_slots");

        suiteData.createExtender()
                .extractSuiteConfigurationJSONResponses("cars");
    }

    @Test
    public void booking_parking_place_success_test() {
        suiteData
                .createTestCaseExecutor("booking_parking_place_success")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking_places", "parkingPlaceId", "parkingPlace.parkingPlaceId"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, new String[]{"parking_places", "parkingPlaceId", "parkingPlace.parkingPlaceId"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseResponseBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void booking_parking_place_with_inactive_car_failure_test() {
        suiteData
                .createTestCaseExecutor("booking_parking_place_with_inactive_car_failure")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start", "parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end", "parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, "parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_slots")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlace.parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("inactive_cars")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"inactive_cars", "id", "car.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void booking_parking_place_not_marked_as_available_failure_test() {
        suiteData
                .createTestCaseExecutor("booking_parking_place_not_marked_as_available_failure")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlace.parkingPlaceId"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void booking_not_existing_parking_place_failure_test() {
        suiteData
                .createTestCaseExecutor("booking_not_existing_parking_place_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void booking_parking_place_without_providing_parking_place_id_failure_test() {
        suiteData
                .createTestCaseExecutor("booking_parking_place_without_providing_parking_place_id_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void booking_parking_place_without_providing_dates_failure_test() {
        suiteData
                .createTestCaseExecutor("booking_parking_place_without_providing_dates_failure")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.SUITE, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start", "parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end", "parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, "parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_slots")
                .extractItemConfigurationJSONResponses(datesExtractor, "booking_dates")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"booking_dates", "start", "start"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"booking_dates", "end", "end"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlace.parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("cars")
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, new String[]{"cars", "id", "car.id"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }


}
