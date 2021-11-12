package no.kristiania.survey;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class AnswerDao extends AbsractDao<Answer>{
    public AnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void setGeneratedKeyStatement(Answer answer, ResultSet rs) throws SQLException {
        answer.setAnswerId(rs.getLong("answer_id"));
    }

    @Override
    protected Answer readFromResultSet(ResultSet rs) throws SQLException {
        Answer answer = new Answer();
        answer.setAnswerId(rs.getLong("answer_id"));
        answer.setAnswerText(rs.getString("answer_text"));
        answer.setQuestion_ID(rs.getLong("question_id"));

        return answer;

    }

    @Override
    protected void setStatement(Answer answer, PreparedStatement statement) throws SQLException {
        statement.setString(1, answer.getAnswerText());
        statement.setLong(2, answer.getQuestion_ID());
    }

    @Override
    public List<Answer> listAll() throws SQLException {
        return super.listWithPreparedStatement("SELECT * FROM answer");
    }


    @Override
    public Answer retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from answer where answer_id = ?", id);
    }


    public void save(Answer answer) throws SQLException {
        String sql = "insert into answer (answer_text, question_id) values (?, ?)";
        super.saveAndUpdateWithStatement(answer, sql);
    }



    /*

    public void save(Answer answer) throws SQLException {

       // Question question = new Question();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answeralternatives (answer_text, question_id) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                setStatement(answer, statement);

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answer.setAnswerId(rs.getLong("answer_id"));
                }
            }
        }
    }

     */


}
