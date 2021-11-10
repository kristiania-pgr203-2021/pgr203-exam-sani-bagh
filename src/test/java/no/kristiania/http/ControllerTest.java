package no.kristiania.http;

import no.kristiania.survey.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    private final HttpServer server = new HttpServer(0);

    public ControllerTest() throws IOException {
    }


    private QuestionDao dao =new QuestionDao(TestData.testDataSource());


    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());



    @BeforeEach
    void initialSave() throws SQLException {
        for (int i=0; i<4; i++) {
            surveyDao.save(SurveyDaoTest.exampleSurvey());
        }
        for (int j=0; j<4; j++) {
            dao.save(QuestionDaoTest.exampleQuestion());
        }
    }
    @Test
    void shouldListQuestions() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        server.addController("/api/questions", new ListQuestionsController(questionDao));

        Question question1 = QuestionDaoTest.exampleQuestion();
        Question question2 = QuestionDaoTest.exampleQuestion();
        questionDao.save(question1);
        questionDao.save(question2);

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/questions");
        assertThat(client.getMessageBody())
                .contains("<h4>" + question1.getQuestionId() + ":" + question1.getTitle() + "</h4>" + "\n" + "<h4> Question: " + question1.getText() + "</h4>" + "\n" +
                        "<h4> Answer one: " + question1.getAnswerOne() + "</h4>" +  "\n" +
                        "<h4>Answer two: " + question1.getAnswerTwo() + "</h4>" + "\n" +
                        "<h4>Answer three: " + question1.getAnswerThree() + "</h4>")
                .contains("<h4>" + question2.getQuestionId() + ":" + question2.getTitle() + "</h4>" + "\n" + "<h4> Question: " + question2.getText() + "</h4>" + "\n" +
                        "<h4> Answer one: " + question2.getAnswerOne() + "</h4>" +  "\n" +
                        "<h4>Answer two: " + question2.getAnswerTwo() + "</h4>" + "\n" +
                        "<h4>Answer three: " + question2.getAnswerThree() + "</h4>")
        ;


    }



    @Test
    void shouldCreateSurveyTitle() throws IOException, SQLException {
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
        server.addController("/api/surveys", new CreateSurveyTitleController(surveyDao));

        HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(),"/api/surveys", "title=Title1"
        );

        assertEquals(303, postClient.getStatusCode());
        assertThat(surveyDao.listAll())
                .anySatisfy(q -> {
                    assertThat(q.getTitle()).isEqualTo("Title1");

                });

    }
}
