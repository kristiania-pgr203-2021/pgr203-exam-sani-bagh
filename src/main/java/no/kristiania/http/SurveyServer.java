package no.kristiania.http;

import no.kristiania.survey.AnswerDao;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.SurveyDao;
import no.kristiania.survey.SurveyUserDao;
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
        AnswerDao answerDao = new AnswerDao(dataSource);
        SurveyUserDao surveyUserDao = new SurveyUserDao(dataSource);


        HttpServer httpServer = new HttpServer(1962);
        httpServer.addController("/api/surveyOptions", new SurveyOptionsController(surveyDao));
        httpServer.addController("POST /api/questions", new CreateSurveyController(questionDao));
        httpServer.addController("GET /api/questions", new ListAllSavedQuestionsController(questionDao));

        httpServer.addController("/api/updateSurvey", new UpdateSurveyConttroller(questionDao));

        httpServer.addController("/api/showQuestions", new ListQuestionsForSurvey(questionDao));
        httpServer.addController("/api/saveAnswer", new SaveAnswerController(answerDao));
        httpServer.addController("/api/newUser", new UserRegisterController(surveyUserDao));

        httpServer.addController("/api/surveyList", new QuestionOptionController(questionDao));

        httpServer.addController("/api/answerAndQuestion", new ListAllAnswersController(answerDao, questionDao));


        httpServer.addController("/api/surveys", new CreateSurveyTitleController(surveyDao));



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
