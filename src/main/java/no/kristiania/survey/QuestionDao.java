package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDao extends AbsractDao<Question>{


    public QuestionDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Question readFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getLong("question_id"));
        question.setTitle(rs.getString("title"));
        question.setText(rs.getString("text"));

        return question;
    }

    @Override
    public List<Question> listAll() throws SQLException {
        return super.listAll("SELECT * FROM question");
    }

    @Override
    public AnswerAlternatives retrieve(long id) throws SQLException {
        return null;
    }

    public List<String> listQuestionText() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select text from question")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<String> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(rs.getString("text"));
                    }
                    return result;
                }
            }
        }
    }

    public void save(Question question) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into question(title, text) values (?, ?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, question.getTitle());
                statement.setString(2, question.getText());


                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    question.setQuestionId(rs.getLong("question_id"));
                }
            }
        }
    }






    /*

    private final DataSource dataSource;

    public QuestionDao(DataSource dataSource) {

        super(dataSource);
        this.dataSource=dataSource;
    }


    @Override
    public Question retrieve(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from question where question_id=?")) {
                statement.setLong(1, id);

                try (ResultSet rs = statement.executeQuery()) {
                    rs.next();

                    return readFromResultSet(rs);
                }
            }
        }
    }

    public List<Question> listAll() throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from question")) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(readFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
    }


    @Override
    public Question readFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();
        question.setQuestionId(rs.getLong("question_id"));
        question.setTitle(rs.getString("title"));
        question.setText(rs.getString("text"));

        return question;
    }

     */


}
