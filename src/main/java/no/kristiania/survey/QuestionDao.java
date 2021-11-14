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
    protected void setGeneratedKeyStatement(Question question, ResultSet rs) throws SQLException {
        question.setQuestionId(rs.getLong("question_id"));
    }

    public void save(Question question) throws SQLException {
        String sql = "insert into question(title, text, answerOne, answerTwo, answerThree, survey_id ) values (?, ?, ?, ?, ?, ?)";
        super.saveAndUpdateWithStatement(question, sql);
    }


    public void update(Question question) throws SQLException {
        String sql = "UPDATE question " +
                "SET title = ?, " +
                "text = ?, " +
                "answerone = ?, " +
                "answertwo= ?, " +
                "answerthree= ? " +
                "WHERE question_id = ?";
        super.saveAndUpdateWithStatement(question, sql);
    }


    @Override
    protected Question readFromResultSet(ResultSet rs) throws SQLException {
        Question question = new Question();

        question.setQuestionId(rs.getLong("question_id"));
        question.setTitle(rs.getString("title"));
        question.setText(rs.getString("text"));
        question.setSurvey_ID(Integer.parseInt(rs.getString("survey_id")));
        question.setAnswerOne(rs.getString("answerOne"));
        question.setAnswerTwo(rs.getString("answerTwo"));
        question.setAnswerThree(rs.getString("answerThree"));

        return question;
    }

    @Override
    public List<Question> listAll() throws SQLException {
        return super.listWithPreparedStatement("SELECT * FROM question");
    }


    public Question retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from question where question_id = ?", id);
    }


    public List<Question> listQuestionBySurveyId(long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select * from question where survey_id= ?")) {
                statement.setLong(1, id);
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




    @Override
    protected void setStatement(Question question, PreparedStatement statement) throws SQLException {
        statement.setString(1, question.getTitle());
        statement.setString(2, question.getText());
        statement.setString(3, question.getAnswerOne());
        statement.setString(4, question.getAnswerTwo());
        statement.setString(5, question.getAnswerThree());
        statement.setLong(6, question.getSurvey_ID());
    }


}
