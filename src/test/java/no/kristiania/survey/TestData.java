package no.kristiania.survey;

import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;


import javax.sql.DataSource;
import java.util.Random;

public class TestData {

    static JdbcDataSource dataSource = new JdbcDataSource();


    public static DataSource testDataSource() {
        dataSource.setUrl("jdbc:h2:mem:surveydb;DB_CLOSE_DELAY=-1");
        Flyway.configure().dataSource(dataSource).load().migrate();
        return dataSource;
    }

    

    private static Random random = new Random();

    public static String pickOne(String... alternates) {
        return alternates[random.nextInt(alternates.length)];
    }

    public static long pickOneLong(long...alternatives) {
        return alternatives[random.nextInt(alternatives.length)];
    }
}
