package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class SurveyUserDao extends AbsractDao<SurveyUser>{
    public SurveyUserDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void setGeneratedKeyStatement(SurveyUser surveyUser, ResultSet rs) throws SQLException {
        surveyUser.setUserId(rs.getLong("user_id"));
    }

    @Override
    protected SurveyUser readFromResultSet(ResultSet rs) throws SQLException {
        SurveyUser surveyUser = new SurveyUser();
        surveyUser.setUserId(rs.getLong("user_id"));
        surveyUser.setFirstName(rs.getString("first_name"));
        surveyUser.setLastName(rs.getString("last_name"));
        surveyUser.setEmail(rs.getString("email"));
        return surveyUser;
    }

    @Override
    protected void setStatement(SurveyUser surveyUser, PreparedStatement statement) throws SQLException {
        statement.setString(1, surveyUser.getFirstName());
        statement.setString(2, surveyUser.getLastName());
        statement.setString(3, surveyUser.getEmail());
    }

    public void save(SurveyUser surveyUser) throws SQLException {
        String sql = "insert into surveyU (first_name, last_name, email) values (?, ?, ?)";
        super.saveAndUpdateWithStatement(surveyUser, sql);
    }


    @Override
    public List<SurveyUser> listAll() throws SQLException {
        return listWithPreparedStatement("select * from SurveyUser");
    }


    @Override
    public SurveyUser retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from SurveyUser where user_id = ?", id );
    }

}
