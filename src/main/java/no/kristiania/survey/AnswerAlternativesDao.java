package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;

public class AnswerAlternativesDao extends Dao{


    protected AnswerAlternativesDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public String retrieve(long id) {
        return null;
    }

    @Override
    public String readFromResultSet(ResultSet rs) {
        return null;
    }
}
