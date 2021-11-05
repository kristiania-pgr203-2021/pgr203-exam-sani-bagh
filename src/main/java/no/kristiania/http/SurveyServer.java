package no.kristiania.http;

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
        SurveyDao surveyDao = new SurveyDao(dataSource);
        HttpServer httpServer = new HttpServer(8080);
        httpServer.addController("/api/questions", new SaveSureveyController(surveyDao));
        httpServer.addController("/api/index", new RetrieveSurveyController(surveyDao));
        logger.info("Starting http://localhost:{}/newQuestionnaire.html", httpServer.getPort());
    }

    private static DataSource createDataSource() throws IOException {
        Properties properties = new Properties();
        try (FileReader reader = new FileReader("pgr-203.properties")) {
            properties.load(reader);
        }

        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(properties.getProperty("db.url"));
        dataSource.setUser(properties.getProperty("db.user"));
        dataSource.setPassword(properties.getProperty("db.password"));
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

}
