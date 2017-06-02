package next.controller.qna;

import static io.restassured.RestAssured.given;

import org.junit.Test;

import io.restassured.http.ContentType;
import next.model.Question;


public class ApiQuestionControllerTest {

	@Test
	public void create() throws Exception {
		Question question = new Question("admin", "TDD는 의미있는 활동인가?", "당근 엄청 의미있는 활동이고 말고..");
		given()
			.auth().preemptive().basic("admin", "password")
			.contentType(ContentType.JSON)
			.body(question)
		.when()
			.post("/api/questions")
		.then()
			.statusCode(201);
	}

}
