package next.controller.user;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import next.config.AppConfig;
import next.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class UserControllerTest {
	private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

	@Before
	public void setup() {
		RestAssured.port = 8080;
		
	}
	@Test
	public void 회원가입() {
		given()
			.contentType(ContentType.HTML)
			.queryParam("userId", "bbq923")
			.queryParam("password", "1234")
			.queryParam("name", "bbq")
			.queryParam("email", "bbq9234@naver.com")
		.when()
			.post("/users")
		.then()
			.statusCode(302);
	}

	@Test
	public void getJSON_from_signup() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		User user = new User("bbq923", "1234", "bbq", "bbq9234@naver.com");
		String jsonUser = mapper.writeValueAsString(user);
		log.debug("json : {}", jsonUser);
		
		User actual = given()
			.auth().preemptive().basic("javajigi", "test1234")
			.contentType(ContentType.JSON)
			.body(jsonUser)
		.when()
			.post("/api/users")
		.then()
			.statusCode(200)
			.extract()
			.as(User.class);
		
		log.debug("{}", actual);
		assertEquals(user, actual);
	}
}
