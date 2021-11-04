package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.List;

public class QuestionDao extends Dao{


    public QuestionDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Question retrieve(long id) {
        return null;
    }

    @Override
    public Question readFromResultSet(ResultSet rs) {
        return null;
    }


}
