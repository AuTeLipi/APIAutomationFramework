package com.LipiAutomation.modules.RestfulBooker;

import com.LipiAutomation.pojos.requestPOJO.restfulbooker.Auth;
import com.LipiAutomation.pojos.responsePOJO.restfulbooker.InvalidTokenResponse;
import com.LipiAutomation.pojos.responsePOJO.restfulbooker.TokenResponse;
import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.LipiAutomation.pojos.requestPOJO.restfulbooker.Booking;
import com.LipiAutomation.pojos.requestPOJO.restfulbooker.BookingDates;
import com.LipiAutomation.pojos.responsePOJO.restfulbooker.BookingResponse;

import java.util.*;

public class PayloadManager {

    // The responsibility of POJO is to serialization and deserialization.

    Gson gson;
    Faker faker;

    // Convert the Java Object into the JSON String to use as Payload.
    // Serialization
    public String createPayloadBookingAsString(){

        Gson gson = new Gson();


        Booking booking = new Booking();
        booking.setFirstname("Lipi");
        booking.setLastname("Dubbaka");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-05");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);
        return gson.toJson(booking);

//        {
//            "firstname" : "Lipi",
//                "lastname" : "Dubbaka",
//                "totalprice" : 3000,
//                "depositpaid" : true,
//                "bookingdates" : {
//            "checkin" : "2025-07-22",
//                    "checkout" : "2025-07-27"
//        },
//            "additionalneeds" : "Breakfast"
//        }
    }
    public String createPayloadBookingAsStringWrongBody(){
        Booking booking = new Booking();
        booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
        booking.setTotalprice(112);
        booking.setDepositpaid(false);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("5025-02-01");
        bookingdates.setCheckout("5025-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("会意; 會意");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;
    }
    public String createPayloadBookingFakerJS(){
        //  This option is you dynamically generate the first name,
        //  last name and other variables.
        faker = new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1, 1000));
        booking.setDepositpaid(faker.random().nextBoolean());

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

        // method with the dynamic data we use,
        // we will fetch the data from excel file.
        // Apache POI
        // String the value, firstName, lastName, and everything, and then we will verify from the response.


    }

    // deserialization ( JSON String to Java Objects)
    // Convert the JSON String to Java Object so that we can verify response Body
    // DeSerialization

    public String createBookingPayloadWithMissingFields(String... fieldsToOmit) {
        Set<String> omitFields = new HashSet<>(Arrays.asList(fieldsToOmit));
        Booking booking = new Booking();

        if (!omitFields.contains("firstname")) {
            booking.setFirstname("Lipi");
        }

        if (!omitFields.contains("lastname")) {
            booking.setLastname("Dubbaka");
        }

        if (!omitFields.contains("totalprice")) {
            booking.setTotalprice(112);
        }

        if (!omitFields.contains("depositpaid")) {
            booking.setDepositpaid(true);
        }

        if (!omitFields.contains("bookingdates")) {
            BookingDates bookingDates = new BookingDates();
            bookingDates.setCheckin("2024-02-01");
            bookingDates.setCheckout("2024-02-05");
            booking.setBookingdates(bookingDates);
        }

        if (!omitFields.contains("additionalneeds")) {
            booking.setAdditionalneeds("Breakfast");
        }

        Gson gson = new Gson();
        return gson.toJson(booking);
    }

    public String createBookingPayloadWithInvalidField(String fieldName, Object invalidValue) {
        Map<String, Object> booking = new LinkedHashMap<>();

        // Default valid values
        booking.put("firstname", "Lipi");
        booking.put("lastname", "Dubbaka");
        booking.put("totalprice", 112);
        booking.put("depositpaid", true);

        Map<String, String> bookingdates = new LinkedHashMap<>();
        bookingdates.put("checkin", "2024-02-01");
        bookingdates.put("checkout", "2024-02-05");
        booking.put("bookingdates", bookingdates);

        booking.put("additionalneeds", "Breakfast");

        // Override the invalid field directly
        if ("checkin".equalsIgnoreCase(fieldName) || "checkout".equalsIgnoreCase(fieldName)) {
            bookingdates.put(fieldName.toLowerCase(), String.valueOf(invalidValue));
        } else {
            booking.put(fieldName.toLowerCase(), invalidValue);
        }

        Gson gson = new Gson();
        return gson.toJson(booking);
    }

    public String createPayloadBookingAsEntireWrongPayload() {
        Map<String, Object> booking = new LinkedHashMap<>();

        booking.put("firstname", 12345);             // Integer instead of String
        booking.put("lastname", true);                // Boolean instead of String
        booking.put("totalprice", "one hundred");    // String instead of int
        booking.put("depositpaid", "yes");            // String instead of boolean

        Map<String, Object> bookingdates = new LinkedHashMap<>();
        bookingdates.put("checkin", "02-30-2024");   // Invalid date string
        bookingdates.put("checkout", 20240205);      // Integer instead of String

        booking.put("bookingdates", bookingdates);
        booking.put("additionalneeds", 458);          // Integer instead of String

        Gson gson = new Gson();
        return gson.toJson(booking);
    }



    public BookingResponse bookingResponseJava(String responseString) {
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }

    public Booking getResponseFromJSON(String responseString) {
        gson = new Gson();
        Booking bookingResponse = gson.fromJson(responseString, Booking.class);
        return bookingResponse;
    }

    // Serialization or deserialization of an object is not present.
    // So we need to create.

    // We convert the JSON string to the Java object for auth.
    // {
    //    "username" : "admin",
    //    "password" : "password123"
    //}

    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("Payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;

    }

    // DeSer ( JSON String -> Java Object
    public String getTokenFromJSON(String tokenResponse){
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }


    // DeSer ( JSON String -> Java Object
    public String getInvalidResponse(String invalidTokenResponse){
        gson = new Gson();
        InvalidTokenResponse tokenResponse1 = gson.fromJson(invalidTokenResponse, InvalidTokenResponse.class);
        return  tokenResponse1.getReason();
    }
}
