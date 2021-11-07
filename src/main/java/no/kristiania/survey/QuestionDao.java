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
        return super.listAllWithPreparedStatement("SELECT * FROM question");
    }

    public Question retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from question where question_id = ?", id);
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

    public List<Question> listQuestionByTitle(String title) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from question where title= ?")) {
                statement.setString(1, title);
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<Question> questions = new ArrayList<>();
                    while (rs.next()) {
                        questions.add(readFromResultSet(rs));
                }

                    return questions;
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




}
