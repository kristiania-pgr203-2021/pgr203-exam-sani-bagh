package no.kristiania.http;


import no.kristiania.survey.SurveyDao;
import no.kristiania.survey.TestData;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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
        assertEquals(404, client.getStatusCode());
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
    void shouldRespondWith200ForKnownRT() throws IOException {
        assertAll(
                () -> assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html").getStatusCode()),
                () -> assertEquals("text/html", new HttpClient("localhost", server.getPort(), "/index.html").getHeader("Content-Type"))
        );
    }


    @Test
    void shouldHandleMoreThanOneRequest() throws IOException {
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
        assertEquals(200, new HttpClient("localhost", server.getPort(), "/index.html")
                .getStatusCode());
    }


    @Test
    void shouldReturnCorrectEncoding() throws IOException, SQLException {
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
        server.addController("/api/surveys", new CreateSurveyTitleController(surveyDao));


        HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/surveys", "title=Spørsmål tittel"
        );

        assertEquals(303, postClient.getStatusCode());
        assertThat(surveyDao.listAll())
                .anySatisfy(s -> {
                    assertThat(s.getTitle()).isEqualTo("Spørsmål tittel");

                });
    }


}
