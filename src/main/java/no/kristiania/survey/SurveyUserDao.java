package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class SurveyUserDao extends AbsractDao<SurveyUser>{
    public SurveyUserDao(DataSource dataSource) {
        super(dataSource);
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

    public void save(SurveyUser surveyUser) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into SurveyUser (first_name, last_name, email) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, surveyUser.getFirstName());
                statement.setString(2, surveyUser.getLastName());
                statement.setString(3, surveyUser.getEmail());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    surveyUser.setUserId(rs.getLong("user_id"));
                }
            }
        }
    }

    @Override
    public List<SurveyUser> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from SurveyUser");
    }


    @Override
    public SurveyUser retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from SurveyUser where user_id = ?", id );
    }

}
