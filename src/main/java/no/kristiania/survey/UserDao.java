package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;

public class UserDao extends Dao{
    protected UserDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public User retrieve(long id) {
        return null;
    }

    @Override
    public User readFromResultSet(ResultSet rs) {
        return null;
    }
}
