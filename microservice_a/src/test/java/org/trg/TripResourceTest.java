package org.trg;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.trg.enums.CarType;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

/**
 * @author Giorgos Sakkas
 * This class implements tests for some basic flows for the /trips REST endpoint
 *
 */

@QuarkusTest
public class TripResourceTest {

	@Test
    public void testValidCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		
		Object car_id = given()
		          .when().contentType(ContentType.JSON)
		          .body(json.toString()).post("/cars")
		          .then()
		             .statusCode(200)
		             .extract()
		             .path("id");
		
		json = new JSONObject();
		json.put("from_lat", 1);
		json.put("from_lon", 1);
		json.put("to_lat", 1);
		json.put("to_lon", 1);
		json.put("car_id",car_id);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(200);
    }
	
	@Test
    public void testMissingFromLatCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

		Object car_id = given()
		          .when().contentType(ContentType.JSON)
		          .body(json.toString()).post("/cars")
		          .then()
		             .statusCode(200)
		             .extract()
		             .path("id");
		
		json = new JSONObject();
		json.put("from_lon", 1);
		json.put("to_lat", 1);
		json.put("to_lon", 1);
		json.put("car_id",car_id);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingFromLonCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		
		Object car_id = given()
		          .when().contentType(ContentType.JSON)
		          .body(json.toString()).post("/cars")
		          .then()
		             .statusCode(200)
		             .extract()
		             .path("id");
		
		json = new JSONObject();
		json.put("from_lat", 1);
		json.put("to_lat", 1);
		json.put("to_lon", 1);
		json.put("car_id",car_id);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingToLatCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		
		Object car_id = given()
		          .when().contentType(ContentType.JSON)
		          .body(json.toString()).post("/cars")
		          .then()
		             .statusCode(200)
		             .extract()
		             .path("id");
		
		json = new JSONObject();
		json.put("from_lon", 1);
		json.put("from_lat", 1);
		json.put("to_lon", 1);
		json.put("car_id",car_id);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingToLonCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		
		Object car_id = given()
		          .when().contentType(ContentType.JSON)
		          .body(json.toString()).post("/cars")
		          .then()
		             .statusCode(200)
		             .extract()
		             .path("id");
		
		json = new JSONObject();
		json.put("from_lat", 1);
		json.put("to_lat", 1);
		json.put("from_lon", 1);
		json.put("car_id",car_id);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingCarCreateTrip() {
		JSONObject json = new JSONObject();
	    
		json.put("from_lat", 1);
		json.put("to_lat", 1);
		json.put("from_lon", 1);
    	
        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/trips")
          .then()
             .statusCode(400);
    }

}