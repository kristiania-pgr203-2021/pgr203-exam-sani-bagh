package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;

public class SurveyDao extends Dao{

    protected SurveyDao(DataSource dataSource) {
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
