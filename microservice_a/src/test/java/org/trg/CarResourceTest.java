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
 * This class implements tests for some basic flows for the /cars REST endpoint
 *
 */

@QuarkusTest
public class CarResourceTest {

	@Test
    public void testValidCreateCar() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/cars")
          .then()
             .statusCode(200);
    }
	
	@Test
    public void testMissingTypeCreateCar() {
		JSONObject json = new JSONObject();
	    
		json.put("brand", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/cars")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingBrandCreateCar() {
		JSONObject json = new JSONObject();

		json.put("type", CarType.COUPE);
		json.put("license_plates", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/cars")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testValidAssignCarToDriver() {
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
	    
		json.put("first_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("last_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("id_number", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

		Object driver_id = given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/drivers")
          .then()
             .statusCode(200)
             .extract()
             .path("id");
		
		given()
        .when().contentType(ContentType.JSON)
        .body(json.toString()).put("/cars/"+car_id+"/assign-to-driver/"+driver_id)
        .then()
           .statusCode(200);
    }

}