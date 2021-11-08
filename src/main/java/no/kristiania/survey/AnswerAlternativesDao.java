package no.kristiania.survey;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AnswerAlternativesDao extends AbsractDao<AnswerAlternatives>{
    public AnswerAlternativesDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected AnswerAlternatives readFromResultSet(ResultSet rs) throws SQLException {
        AnswerAlternatives answerAlternatives = new AnswerAlternatives();
        answerAlternatives.setAnswerId(rs.getLong("answer_id"));
        answerAlternatives.setAnswerText(rs.getString("answer_text"));
        answerAlternatives.setQuestion_ID(rs.getLong("question_id"));

        return answerAlternatives;

    }

    @Override
    public List<AnswerAlternatives> listAll() throws SQLException {
        return super.listAllWithPreparedStatement("SELECT * FROM answerAlternatives");
    }


    @Override
    public AnswerAlternatives retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from answerAlternatives where answer_id = ?", id);
    }

    public void save(AnswerAlternatives answerAlternatives) throws SQLException {

        Question question = new Question();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answeralternatives (answer_text, question_id) values (?, " +
                            "(select question_id from question where text = ?))",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, answerAlternatives.getAnswerText());
                statement.setString(2, question.getText());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answerAlternatives.setAnswerId(rs.getLong("answer_id"));
                }
            }
        }
    }


}
