package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.QuestionDaoTest;
import no.kristiania.survey.TestData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateQuestionControllerTest {

    private final HttpServer server = new HttpServer(0);

    public CreateQuestionControllerTest() throws IOException {
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
                .contains("<h4>" + question1.getTitle() + "</h4>" + "\n" +  "<h4>" + question1.getText() + "</h4>")
                .contains("<h4>" + question2.getTitle() + "</h4>" + "\n" +  "<h4>" + question2.getText() + "</h4>")
        ;
    }
}
