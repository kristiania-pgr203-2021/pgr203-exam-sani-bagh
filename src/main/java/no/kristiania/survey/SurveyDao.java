package no.kristiania.survey;


import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class SurveyDao extends AbsractDao<Survey>{

    private final DataSource dataSource;

    public SurveyDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    protected void setGeneratedKeyStatement(Survey survey, ResultSet rs) throws SQLException {
        survey.setId(rs.getLong("survey_id"));
    }

    @Override
    public Survey retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from survey where survey_id = ?", id);

    }

    public void save(Survey survey) throws SQLException {
        String sql = "insert into survey(title) values (?)";
        super.saveAndUpdateWithStatement(survey, sql);
    }


    public Survey readFromResultSet(ResultSet rs) throws SQLException {
    Survey survey = new Survey();
    survey.setId(rs.getLong("survey_id"));
    survey.setTitle(rs.getString("title"));

        return survey;
    }

    @Override
    protected void setStatement(Survey survey, PreparedStatement statement) throws SQLException {
        statement.setString(1, survey.getTitle());
    }

    @Override
    public List<Survey> listAll() throws SQLException {
        return listWithPreparedStatement("select * from survey");
    }
}
