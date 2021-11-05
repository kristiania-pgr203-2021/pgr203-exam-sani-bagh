package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.QuestionDaoTest;
import no.kristiania.survey.TestData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpServerTest {

    private final HttpServer server = new HttpServer(0);

    HttpServerTest() throws IOException {
    }

    @Test
    void shouldReturn404ForUnknownRequestTarget() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals(404, client.getStatusCode());
    }

    @Test
    void shouldRespondWithRequestTargetIn404() throws IOException {
        HttpClient client = new HttpClient("localhost", server.getPort(), "/non-existing");
        assertEquals("File not found: /non-existing", client.getMessageBody());
    }

    @Test
    void shouldServeFiles() throws IOException {
        String fileContent = "A file created at " + LocalTime.now();
        Files.write(Paths.get("target/test-classes/example-file.txt"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.txt");
        assertEquals(fileContent, client.getMessageBody());
        assertEquals("text/plain", client.getHeader("Content-Type"));
    }

    @Test
    void shouldUseFileExtensionForContentType() throws IOException {
        String fileContent = "<p>Hello</p>";
        Files.write(Paths.get("target/test-classes/example-file.html"), fileContent.getBytes());

        HttpClient client = new HttpClient("localhost", server.getPort(), "/example-file.html");
        assertEquals("text/html", client.getHeader("Content-Type"));
    }

    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
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
