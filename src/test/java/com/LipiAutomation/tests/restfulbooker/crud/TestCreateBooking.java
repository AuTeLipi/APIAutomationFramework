package com.LipiAutomation.tests.restfulbooker.crud;

import com.LipiAutomation.asserts.AssertActions;
import com.LipiAutomation.base.BaseTest;
import com.LipiAutomation.endpoints.APIConstants;
import com.LipiAutomation.pojos.responsePOJO.restfulbooker.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;


public class TestCreateBooking extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Lipi Dubbaka")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        // Setup will first and making the request - Part - 1
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString())
                .log().all().post();

        //Extraction Part - 2
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());


        // Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 200);
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Lipi");
        assertActions.verifyStringKey(bookingResponse.getBooking().getLastname(), "Dubbaka");
        assertActions.verifyIntKey(bookingResponse.getBooking().getTotalprice(), 112, "Total price does not match");
        assertActions.verifyBooleanKey(bookingResponse.getBooking().getDepositpaid(), true);
        assertActions.verifyStringKey(bookingResponse.getBooking().getBookingdates().getCheckin(), "2024-02-01");
        assertActions.verifyStringKey(bookingResponse.getBooking().getBookingdates().getCheckout(), "2024-02-05");
        assertActions.verifyStringKey(bookingResponse.getBooking().getAdditionalneeds(), "Breakfast");

    }


    @Test(groups = "reg", priority = 2)
    @Owner("Lipi Dubbaka")
    @Description("TC#2 - Verify that the Booking can be Created, When Payload is CHINESE")
    public void testCreateBookingPOST_POSITIVE_CHINESE() {


        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().
                body(payloadManager.createPayloadBookingAsStringWrongBody()).log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        //Extraction Part - 2
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        assertActions.verifyStatusCode(response, 200);

    }

    @Test(groups = "reg", priority = 3)
    @Owner("Lipi Dubbaka")
    @Description("TC#3 - Verify that the Booking can be Created, When Payload is RANDOM")
    public void testCreateBookingPOST_POSITIVE_FAKER_RANDOM_DATA() {
        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());

        assertActions.verifyStatusCode(response, 200);

    }


    @Test(groups = "reg", priority = 4)
    @Owner("Lipi Dubbaka")
    @Description("TC#4 - Verify that the Booking can't be Created, When Payload is null")
    public void testCreateBookingPOST_Negative() {

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().
                body("{}").log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(500);

        assertActions.verifyStatusCode(response, 500);

    }

        @Test(groups = "reg", priority = 5)
    @Owner("Lipi Dubbaka")
    @Description("TC#5 - Verify that the Booking can be Created, When Payload is RANDOM")
    public void testCreateBookingPOST_NEGATIVE_WRONG() {
        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        requestSpecification.contentType(ContentType.HTML);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(500);

            assertActions.verifyStatusCode(response, 500);

    }

    @Test(groups = "reg", priority = 6)
    @Owner("Lipi Dubbaka")
    @Description("TC#6 - Verify that the Booking can't be Created, URL is wrong")
    public void testCreateBookingPOST_NEGATIVE_BASE_URL() {
        // Setup and Making a Request.
        requestSpecification.baseUri(APIConstants.APP_VWO_URL);
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        requestSpecification.contentType(ContentType.HTML);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);

        assertActions.verifyStatusCode(response, 404);

    }


    @Test(groups = "reg", priority = 7)
    @Owner("Lipi Dubbaka")
    @Description("TC#7 - Verify that the Booking can't be Created, Firstname Missing")
    public void testCreateBookingPOST_NEGATIVE_FIRSTNAME_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("firstname");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "firstname");

    }

    @Test(groups = "reg", priority = 8)
    @Owner("Lipi Dubbaka")
    @Description("TC#8 - Verify that the Booking can't be Created,Lastname missing")
    public void testCreateBookingPOST_NEGATIVE_Lastname_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("lastname");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "lastname");

    }

    @Test(groups = "reg", priority = 9)
    @Owner("Lipi Dubbaka")
    @Description("TC#9 - Verify that the Booking can't be Created,Totalprice missing")
    public void testCreateBookingPOST_NEGATIVE_Totalprice_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("Totalprice");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "Totalprice");

    }

    @Test(groups = "reg", priority = 10)
    @Owner("Lipi Dubbaka")
    @Description("TC#10 - Verify that the Booking can't be Created,DepositPaid missing")
    public void testCreateBookingPOST_NEGATIVE_DepositPaid_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("depositpaid");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "depositpaid");

    }

    @Test(groups = "reg", priority = 11)
    @Owner("Lipi Dubbaka")
    @Description("TC#11 - Verify that the Booking can't be Created,BookingDates missing")
    public void testCreateBookingPOST_NEGATIVE_BookingDates_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("bookingdates");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "bookingdates");

    }

    @Test(groups = "reg", priority = 12)
    @Owner("Lipi Dubbaka")
    @Description("TC#12 - Verify that the Booking can't be Created,AdditionalNeeds missing")
    public void testCreateBookingPOST_NEGATIVE_AdditionalNeeds_MISSING() {
        String payload = payloadManager.createBookingPayloadWithMissingFields("additionalneeds");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);
        assertActions.verifyKeyIsMissing(response, "additionalneeds");

    }

    @Test(groups = "reg", priority = 13)
    @Owner("Lipi Dubbaka")
    @Description("TC#13 - Verify that the Booking can't be Created,Invalid firstname")
    public void testCreateBookingPOST_NEGATIVE_Invalid_Firstname() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("firstname", 676);

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 14)
    @Owner("Lipi Dubbaka")
    @Description("TC#14 - Verify that the Booking can't be Created,Invalid lastname")
    public void testCreateBookingPOST_NEGATIVE_Invalid_Lastname() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("lastname", 548);

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 15)
    @Owner("Lipi Dubbaka")
    @Description("TC#15 - Verify that the Booking can't be Created,Invalid TotalPrice")
    public void testCreateBookingPOST_NEGATIVE_Invalid_TotalPrice() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("totalprice", "InvalidDataType");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 16)
    @Owner("Lipi Dubbaka")
    @Description("TC#16 - Verify that the Booking can't be Created,Invalid DepositPaid ")
    public void testCreateBookingPOST_NEGATIVE_Invalid_DepositPaid() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("depositpaid", "InvalidDataType");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 17)
    @Owner("Lipi Dubbaka")
    @Description("TC#17 - Verify that the Booking can't be Created,Invalid CheckInDate")
    public void testCreateBookingPOST_NEGATIVE_Invalid_CheckInDate() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("checkin", "02-01-2024");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 18)
    @Owner("Lipi Dubbaka")
    @Description("TC#18 - Verify that the Booking can't be Created,Invalid CheckOutDate")
    public void testCreateBookingPOST_NEGATIVE_Invalid_CheckOutDate() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("checkout", "InvalidDataType");

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 19)
    @Owner("Lipi Dubbaka")
    @Description("TC#19 - Verify that the Booking can't be Created,Invalid AdditionalNeeds")
    public void testCreateBookingPOST_NEGATIVE_Invalid_AdditionalNeeds() {
        String payload = payloadManager.createBookingPayloadWithInvalidField("additionalneeds", 458);

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }

    @Test(groups = "reg", priority = 20)
    @Owner("Lipi Dubbaka")
    @Description("TC#20 - Verify that the Booking can't be Created,Entire Wrong Payload")
    public void testCreateBookingPOST_NEGATIVE_EntireWrongPayload() {
        String payload = payloadManager.createPayloadBookingAsEntireWrongPayload();

        response = RestAssured.given(requestSpecification)
                .when().body(payload)
                .log().all().post();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        //Validation and verification via the AssertJ, TestNG Part - 3
        assertActions.verifyStatusCode(response, 404);

    }


}
