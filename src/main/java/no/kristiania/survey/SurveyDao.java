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
    public Survey retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from survey where survey_id = ?", id);
        

    }


    public void save(Survey survey) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into survey(title, question_id, answer_id) values (?, " +
                            "(select question_id from question where question_id = ?), " +
                            "(select answer_id from answerAlternatives where answer_id = ?))",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, survey.getTitle());
                statement.setLong(2, survey.getQuestionId());
                statement.setLong(3, survey.getAnswerId());


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
    survey.setQuestionId(rs.getLong("question_id"));
    survey.setAnswerId(rs.getLong("answer_id"));


        return survey;
    }

    @Override
    public List<Survey> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from survey");
    }
}
