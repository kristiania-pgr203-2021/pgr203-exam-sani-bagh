package no.kristiania.survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserAnswerDaoTest {

    private UserAnswerDao dao = new UserAnswerDao(TestData.testDataSource());
    private SurveyUserDao surveyUserDao = new SurveyUserDao(TestData.testDataSource());
    private SurveyUserDaoTest surveyUserDaoTest =new SurveyUserDaoTest();
    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
    private SurveyDaoTest surveyDaoTest = new SurveyDaoTest();
    private  QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    private QuestionDaoTest questionDaoTest = new QuestionDaoTest();
    private AnswerDao answerDao = new AnswerDao(TestData.testDataSource());


/*
    @BeforeEach
    void initialSave() throws SQLException {
        for (int i = 0; i < 10; i++) {
            dao.save(exampleUserAnswer());
        }
        for (int j = 0; j < 10; j++) {
            surveyUserDao.save(surveyUserDaoTest.exampleUser());
        }
        for (int k = 0; k < 10; k++) {
            surveyDao.save(surveyDaoTest.exampleSurvey());
        }
        for (int l = 0; l < 10; l++) {
            questionDao.save(questionDaoTest.exampleQuestion());
        }

     }

     @Test
     void shouldRetrieveSavedUserAnswer() throws SQLException {
        UserAnswer userAnswer = exampleUserAnswer();
        dao.save(userAnswer);

        assertThat(dao.retrieve(userAnswer.getUserAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(userAnswer);
     }

    @Test
    void shouldListAllUserAnswers() throws SQLException {
        UserAnswer userAnswer = exampleUserAnswer();
        dao.save(userAnswer);
        UserAnswer userAnswerTwo = exampleUserAnswer();
        dao.save(userAnswerTwo);

        assertThat(dao.listAll())
                .extracting(UserAnswer::getUserAnswerId)
                .contains(userAnswer.getUserAnswerId(), userAnswerTwo.getUserAnswerId());
    }

    public static UserAnswer exampleUserAnswer() {
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setUserId(TestData.pickOneLong(1, 2, 3, 4));
        userAnswer.setSurveyId(TestData.pickOneLong(5, 6, 7, 8));
        userAnswer.setQuestionId(TestData.pickOneLong(5, 2, 3, 4));
        userAnswer.setAnswerID(TestData.pickOneLong(1, 2, 3, 4));

        return userAnswer;
    }*/
}
