package com.LipiAutomation.asserts;

import com.LipiAutomation.pojos.requestPOJO.restfulbooker.Booking;
import io.restassured.response.Response;
import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.assertTrue;

public class AssertActions {
    public void verifyResponseBody(String actual, String expected, String description){
        assertEquals(actual,expected,description);
    }
    public void verifyResponseBody(int actual, int expected, String description) {
        assertEquals(actual, expected, description);
    }

    public void verifyStatusCode(Response response, int expected) {
        assertEquals(response.getStatusCode(),expected);
    }



    public void verifyStringKey(String keyExpect,String keyActual){
        // AssertJ
        assertThat(keyExpect).isNotNull();
        assertThat(keyExpect).isNotBlank();
        assertThat(keyExpect).isEqualTo(keyActual);

    }

    public void verifyStringKeyNotNull(Integer keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }
    public void verifyStringKeyNotNull(String keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }
    public void verifyIntKey(int keyActual, int keyExpect, String description) {
        assertEquals(keyActual, keyExpect, description);
    }

    public void verifyBooleanKey(boolean keyActual, boolean keyExpect) {
        assertEquals(keyActual, keyExpect);
    }

    public void verifyTrue(boolean keyExpect){
        assertTrue(keyExpect);
    }

    public void verifyBookingDates(String checkinActual, String checkinExpect,
                                   String checkoutActual, String checkoutExpect) {
        verifyStringKey(checkinActual, checkinExpect);
        verifyStringKey(checkoutActual, checkoutExpect);
    }

    public void verifyBooking(Booking booking, String expectedFirstname, String expectedLastname,
                              int expectedTotalprice, boolean expectedDepositpaid,
                              String expectedCheckin, String expectedCheckout,
                              String expectedAdditionalneeds) {

        verifyStringKey(booking.getFirstname(), expectedFirstname);
        verifyStringKey(booking.getLastname(), expectedLastname);
        verifyIntKey(booking.getTotalprice(), expectedTotalprice, "Total price does not match");
        verifyBooleanKey(booking.getDepositpaid(), expectedDepositpaid);

        // Verify nested bookingdates
        verifyBookingDates(
                booking.getBookingdates().getCheckin(),
                expectedCheckin,
                booking.getBookingdates().getCheckout(),
                expectedCheckout
        );

        verifyStringKey(booking.getAdditionalneeds(), expectedAdditionalneeds);
    }


    public void verifyKeyIsMissing(Response response, String key) {
        try {
            Object value = response.jsonPath().get(key);
            assertThat(value)
                    .withFailMessage("Expected key '%s' to be missing, but found value: %s", key, value)
                    .isNull();
        } catch (Exception e) {
            System.out.println("Skipping key check — response is not valid JSON.");
            System.out.println("Actual response: " + response.asString());
        }
    }


}
