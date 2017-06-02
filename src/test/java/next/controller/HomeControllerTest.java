package next.controller;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

public class HomeControllerTest {
	private static final Logger log = LoggerFactory.getLogger(HomeControllerTest.class);
	
	@Before
	public void setup() {
		RestAssured.port = 8080;
	}
	
	@Test
	public void home() throws Exception {
		String body = given()
			.contentType(ContentType.HTML)
		.when()
			.get("/")
		.then()
			.statusCode(200)
			.extract()
			.asString();
		
		log.debug(body);
	}
}
