package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;

public class AnswerAlternativesDao extends Dao{

    private final DataSource dataSource;

    protected AnswerAlternativesDao(DataSource dataSource) {
        super(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public AnswerAlternatives retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from answerAlternatives where answer_id = ?")) {
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


        return answerAlternatives;
    }
}
