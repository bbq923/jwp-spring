package next.controller.qna;


import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import next.config.AppConfig;
import next.dao.QuestionDao;
import next.model.Question;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class QuestionControllerTest {
	private static final Logger log = LoggerFactory.getLogger(QuestionControllerTest.class);
	
	@Autowired
	QuestionDao questionDao;
	Question savedQues; 
	
	@Before
	public void setup() {
		RestAssured.port = 8080;
		savedQues = questionDao.insert(new Question("bbq923", "title1", "content2"));
	}

	@Test
	public void findQuestion() {
		Question actual = given()
			.contentType(ContentType.JSON)
		.when()
			.get(String.format("/api/questions/%d", savedQues.getQuestionId()))
		.then()
			.statusCode(200)
			.extract()
			.as(Question.class);
		
		log.debug("ques : {}", actual);
		assertEquals(savedQues, actual);
	}

}
