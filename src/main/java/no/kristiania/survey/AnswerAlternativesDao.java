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
        //answerAlternatives.setQuestion_ID(rs.getLong("question_ID"));


        return answerAlternatives;

    }

    @Override
    public List<AnswerAlternatives> listAll() throws SQLException {
        return super.listAll("SELECT * FROM question");
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
    public void save(AnswerAlternatives answerAlternatives) {

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

    public void save(AnswerAlternatives answerAlternatives) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into answerAlternatives (text) values (?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, answerAlternatives.getAnswerText());



                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    answerAlternatives.setAnswerId(rs.getLong("answer_id"));
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
