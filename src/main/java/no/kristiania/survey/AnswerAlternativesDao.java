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

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answeralternatives (answer_text, question_id) values (?, " +
                            "(select question_id from question where question_id = ?))",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, answerAlternatives.getAnswerText());
                statement.setLong(2, answerAlternatives.getQuestion_ID());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answerAlternatives.setAnswerId(rs.getLong("answer_id"));
                }
            }
        }
    }


    /*

    private final DataSource dataSource;

    public AnswerAlternativesDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public AnswerAlternatives retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answerAlternatives inner join question on " +
                    " question.question_id = answerAlternatives.question_ID where answer_id = ?")) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSet(rs);
                }
            }
        }
    }



    @Override
    public AnswerAlternatives readFromResultSet(ResultSet rs) throws SQLException {
        AnswerAlternatives answerAlternatives = new AnswerAlternatives();
        answerAlternatives.setAnswerId(rs.getLong("answer_id"));
        answerAlternatives.setAnswerText(rs.getString("answer_text"));
        //answerAlternatives.setQuestion_ID(rs.getLong("question_ID"));


        return answerAlternatives;
    }

     */
}
