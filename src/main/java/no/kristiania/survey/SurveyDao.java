package no.kristiania.survey;


import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyDao extends AbsractDao<Survey>{

    private final DataSource dataSource;

    public SurveyDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public Survey retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from survey where survey_id = ?", id);
        

    }


    public void save(Survey survey) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into survey(title) values (?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, survey.getTitle());


                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    survey.setId(rs.getLong("survey_id"));
                }
            }
        }
    }




    public Survey readFromResultSet(ResultSet rs) throws SQLException {
    Survey survey = new Survey();
    survey.setId(rs.getLong("survey_id"));
    survey.setTitle(rs.getString("title"));


        return survey;
    }

    @Override
    public List<Survey> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from survey");
    }
}
