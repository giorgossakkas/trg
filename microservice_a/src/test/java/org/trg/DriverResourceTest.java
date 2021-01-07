package org.trg;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

/**
 * @author Giorgos Sakkas
 * This class implements tests for some basic flows for the /drivers REST endpoint
 *
 */

@QuarkusTest
public class DriverResourceTest {

	@Test
    public void testValidCreateDriver() {
		JSONObject json = new JSONObject();
	    
		json.put("first_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("last_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("id_number", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/drivers")
          .then()
             .statusCode(200);
    }
	
	@Test
    public void testMissingFirstNameCreateDriver() {
		JSONObject json = new JSONObject();
	    
		json.put("last_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("id_number", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/drivers")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingLastNameCreateDriver() {
		JSONObject json = new JSONObject();
	    
		json.put("first_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("id_number", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/drivers")
          .then()
             .statusCode(400);
    }
	
	@Test
    public void testMissingIdNumberCreateDriver() {
		JSONObject json = new JSONObject();
	    
		json.put("first_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));
		json.put("last_name", RandomStringUtils.random(32, Boolean.valueOf(true), Boolean.valueOf(true)));

        given()
          .when().contentType(ContentType.JSON)
          .body(json.toString()).post("/drivers")
          .then()
             .statusCode(400);
    }

}