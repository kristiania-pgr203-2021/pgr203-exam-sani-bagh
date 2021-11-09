package no.kristiania.http;

import no.kristiania.survey.AnswerAlternativesDao;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.SurveyDao;
import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class SurveyServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    public static void main(String[] args) throws IOException {
        DataSource dataSource = createDataSource();
        QuestionDao questionDao = new QuestionDao(dataSource);
        SurveyDao surveyDao = new SurveyDao(dataSource);
        AnswerAlternativesDao answerAlternativesDao = new AnswerAlternativesDao(dataSource);

        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/questions", new CreateQuestionController(questionDao));
        httpServer.addController("/api/questions", new ListQuestionsController(questionDao));
        httpServer.addController("/api/questionOptions", new QuestionOptionsController(questionDao));
        httpServer.addController("/api/tasks", new CreateAnswerOptionsController(answerAlternativesDao));
        httpServer.addController("/api/index", new RetrieveSurveysController(surveyDao));
        logger.info("Starting http://localhost:{}/index.html", httpServer.getPort());

    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("dataSource.url", "jdbc:postgresql://localhost:5432/survey_db"));
        dataSource.setUser(properties.getProperty("dataSource.user", "survey_dbuser"));
        dataSource.setPassword(properties.getProperty("dataSource.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

}
