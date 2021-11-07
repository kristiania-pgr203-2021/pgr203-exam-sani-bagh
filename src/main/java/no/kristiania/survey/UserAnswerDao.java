package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserAnswerDao extends AbsractDao<UserAnswer>{
    protected UserAnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public UserAnswer retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from userAnswer where userAnswer_id = ?", id);
    }

    @Override
    public UserAnswer readFromResultSet(ResultSet rs) {
        return null;
    }

    @Override
    public List<UserAnswer> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from userAnswer");
    }

}
