package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserAnswerDao extends AbsractDao<UserAnswer>{
    protected UserAnswerDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected void setGeneratedKeyStatement(UserAnswer object, ResultSet rs) throws SQLException {

    }

    @Override
    public UserAnswer retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from userAnswer where user_answer_id = ?", id);
    }

    @Override
    public UserAnswer readFromResultSet(ResultSet rs) throws SQLException {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUserAnswerId(rs.getLong("user_answer_id"));
        userAnswer.setUserId(rs.getLong("user_id"));
        userAnswer.setSurveyId(rs.getLong("survey_id"));
        userAnswer.setQuestionId(rs.getLong("question_id"));
        userAnswer.setAnswerID(rs.getLong("answer_id"));
        return userAnswer;
    }

    @Override
    protected void setStatement(UserAnswer userAnswer, PreparedStatement statement) throws SQLException {
        statement.setLong(1, userAnswer.getUserId());
        statement.setLong(2, userAnswer.getSurveyId());
        statement.setLong(3, userAnswer.getQuestionId());
        statement.setLong(4, userAnswer.getAnswerID());
    }

    @Override
    public List<UserAnswer> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from userAnswer");
    }

    public void save(UserAnswer userAnswer) throws SQLException {
        String sql = "insert into userAnswer(user_id, survey_id, question_id, answer_id) values (?, ?, ?, ?)";
        super.saveAndUpdateWithStatement(userAnswer, sql);
    }


    /*

    public void save(UserAnswer userAnswer) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into userAnswer(user_id, survey_id, question_id, answer_id) values (" +
                            "(select user_id from surveyUser where user_id = ?), " +
                            "(select survey_id from survey where survey_id = ?), " +
                            "(select  question_id from question where question_id = ?), " +
                            "(select answer_id from answerAlternatives where answer_id = ?))",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setLong(1, userAnswer.getUserId());
                statement.setLong(2, userAnswer.getSurveyId());
                statement.setLong(3, userAnswer.getQuestionId());
                statement.setLong(4, userAnswer.getAnswerID());


                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    while (rs.next()) {
                        userAnswer.setUserAnswerId(rs.getLong("user_answer_id"));
                    }
                }
            }
        }
    }

     */





    }
