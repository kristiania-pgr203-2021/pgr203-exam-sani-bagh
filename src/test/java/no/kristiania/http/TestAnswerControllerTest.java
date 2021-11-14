package no.kristiania.http;

import no.kristiania.survey.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestAnswerControllerTest {
    private final HttpServer server = new HttpServer(0);

    public TestAnswerControllerTest() throws IOException {
    }

    private QuestionDao dao = new QuestionDao(TestData.testDataSource());


    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

    private AnswerDao answerDao = new AnswerDao(TestData.testDataSource());

    @BeforeEach
    void initialSave() throws SQLException {
        for (int i = 0; i < 4; i++) {
            surveyDao.save(SurveyDaoTest.exampleSurvey());
        }
        for (int j = 0; j < 4; j++) {
            dao.save(QuestionDaoTest.exampleQuestion());
        }
    }

    @Test
    void shouldListQuestionsWithAnswers() throws SQLException, IOException {
        AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        server.addController("/api/answerAndQuestion", new ListAllAnswersController(answerDao, questionDao));

        Question question = QuestionDaoTest.exampleQuestion();
        Question question2 = QuestionDaoTest.exampleQuestion();
        Question question3 = QuestionDaoTest.exampleQuestion();
        Question question4 = QuestionDaoTest.exampleQuestion();
        Answer answer = AnswerDaoTest.exampleAnswer();
        Answer answer2 = AnswerDaoTest.exampleAnswer();
        Answer answer3 = AnswerDaoTest.exampleAnswer();
        Answer answer4 = AnswerDaoTest.exampleAnswer();
        questionDao.save(question);
        questionDao.save(question2);
        questionDao.save(question3);
        questionDao.save(question4);
        answerDao.save(answer);
        answerDao.save(answer2);
        answerDao.save(answer3);
        answerDao.save(answer4);

        HttpClient client = new HttpClient("localhost", server.getPort(),"/api/answerAndQuestion");
        assertThat(client.getMessageBody())
                .contains("<h1>" + question.getTitle() + "</h1>" +
                        "<h3>" + question.getText() + "</h3>" +
                        "<ul>" + answer.getAnswerText() + "</ul>")
                .contains("<h1>" + question2.getTitle() + "</h1>" +
                        "<h3>" + question2.getText() + "</h3>" +
                        "<ul>" + answer2.getAnswerText() + "</ul>")
                .contains("<h1>" + question3.getTitle() + "</h1>" +
                        "<h3>" + question3.getText() + "</h3>" +
                        "<ul>" + answer3.getAnswerText() + "</ul>")
                .contains("<h1>" + question4.getTitle() + "</h1>" +
                        "<h3>" + question4.getText() + "</h3>" +
                        "<ul>" + answer4.getAnswerText() + "</ul>");
    }
}
