package com.mobica.rnd.parking.parkingbetests;

import com.mobica.rnd.parking.parkingbetests.support.*;
import com.mobica.rnd.parking.parkingbetests.support.extractors.DatesExtractor;
import com.mobica.rnd.parking.parkingbetests.support.extractors.IDataExtractor;
import org.json.simple.JSONArray;
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
 * Created by int_eaja on 2017-08-07.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class Issue_16_ParkingSlotsAvailableForBookingTest {

    private static final String ISSUE_NAME = "16-ParkingSlotsAvailableForBooking";

    @Autowired
    private RestAssuredProcessor processor;
    private TestSuiteData suiteData;

    private DatesExtractor datesExtractor;
    private IDataExtractor fakeIdsExtractor, emptyDatesExtractor;

    @PostConstruct
    public void setUp() {
        suiteData = new TestSuiteData(processor.getBaseURL(), ISSUE_NAME);
        initExtractors();

    }


    @Test
    public void mark_parking_slots_available_for_booking_success_test() {

        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_success")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_using_dates_boundary_values_success_test() {

        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_using_dates_boundary_values_success")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_using_parking_places_boundary_values_success_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_using_parking_places_boundary_values_success")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses(datesExtractor, "primary_dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"primary_dates", "start", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"primary_dates", "end", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, "primary_parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("primary_parking_slots")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_using_different_parking_places_values_success_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_using_different_parking_places_values_success")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses(datesExtractor, "primary_dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"primary_dates", "start", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"primary_dates", "end", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ONE, SourceDataType.ITEM, "primary_parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{0, 0})
                .extractItemConfigurationJSONResponses("primary_parking_slots")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ONE, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{1, 0})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_for_past_dates_failure_test() {

        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_for_past_dates_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    @Ignore
    public void mark_parking_slots_available_for_booking_for_dates_after_selected_limit_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_for_dates_after_selected_limit_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_with_end_date_before_start_date_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_with_end_date_before_start_date_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_when_some_of_them_already_marked_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_when_some_of_them_already_marked_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, "primary_parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("primary_parking_slots")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);

    }

    @Test
    public void mark_parking_slots_available_for_booking_when_some_of_them_do_not_exist_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_when_some_of_them_do_not_exist_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extractItemConfigurationJSONResponses(fakeIdsExtractor, "fake_parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extendTestCaseRequestBody(DataAssociationType.ONE_TO_ONE, SourceDataType.ITEM, new String[]{"fake_parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{0, 0})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_using_dates_boundary_values_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_using_dates_boundary_values_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    public void mark_parking_slots_available_for_booking_using_parking_places_boundary_values_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_using_parking_places_boundary_values_failure")
                .extractItemConfigurationJSONResponses(datesExtractor, "dates")
                .extractItemConfigurationJSONResponses(datesExtractor, "primary_dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"primary_dates", "start", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"primary_dates", "end", "primary_parking_slots"}, 0)
                .extendConfigurationItemRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, "primary_parking_slots", new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("primary_parking_slots")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }


    @Test
    @Ignore
    public void mark_parking_slots_available_for_booking_without_passing_dates_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_without_passing_dates_failure")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }

    @Test
    @Ignore
    public void mark_parking_slots_available_for_booking_with_passing_empty_dates_failure_test() {
        suiteData
                .createTestCaseExecutor("mark_parking_slots_available_for_booking_with_passing_empty_dates_failure")
                .extractItemConfigurationJSONResponses(emptyDatesExtractor, "dates")
                .extractItemConfigurationJSONResponses("parking")
                .extendConfigurationItemRequestBody(DataAssociationType.ONE_TO_ALL, SourceDataType.ITEM, "parking_places", new String[]{"parking", "id", "parking.id"}, new int[]{-1, -1})
                .extractItemConfigurationJSONResponses("parking_places")
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "start", new String[]{"dates", "start"})
                .extendTestCaseRequestQueryParam(SourceDataType.ITEM, "end", new String[]{"dates", "end"})
                .extendTestCaseRequestBody(DataAssociationType.ALL_TO_ALL, SourceDataType.ITEM, new String[]{"parking_places", "parkingPlaceId", "parkingPlaceId"}, new int[]{-1, -1})
                .performTests(processor, suiteData);
    }


    private void initExtractors() {
        datesExtractor = new DatesExtractor();
        emptyDatesExtractor = new IDataExtractor() {
            @Override
            public JSONObject[] extractData(JSONObject obj, String key) {
                JSONArray data = (JSONArray) obj.get("data");
                JSONObject datesObj = (JSONObject) data.get(0);
                String start = (String) datesObj.get("start");
                String end = (String) datesObj.get("end");
                return new JSONObject[]{getDates(start, end)};
            }


            private JSONObject getDates(String start, String end) {
                JSONObject o = new JSONObject();
                o.put("start", start);
                o.put("end", end);
                return o;
            }
        };
        fakeIdsExtractor = new IDataExtractor() {
            @Override
            public JSONObject[] extractData(JSONObject obj, String key) {
                JSONArray data = (JSONArray) obj.get("data");
                JSONObject idsObj = (JSONObject) data.get(0);
                String fakeId = (String) idsObj.get("parkingPlaceId");
                JSONObject o = new JSONObject();
                o.put("parkingPlaceId", fakeId);
                return new JSONObject[]{o};
            }
        };
    }

}
