package no.kristiania.survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {
    private AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
    private QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());

    @BeforeEach
    void initialSave() throws SQLException {
        for (int n=0; n < 10; n++) {
            surveyDao.save(SurveyDaoTest.exampleSurvey());
        }
        for (int i=0; i<10; i++) {
            questionDao.save(QuestionDaoTest.exampleQuestion());
        }
        for (int j=0; j<10; j++) {
            answerDao.save(exampleAnswer());
        }
    }

    @Test
    void shouldRetrieveSavedAnswer() throws SQLException {
        Answer answer = exampleAnswer();
        answerDao.save(answer);

        assertThat(answerDao.retrieve(answer.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answer);
    }

    @Test
    void shouldListAllAnswers() throws SQLException {
        Answer answerOne = exampleAnswer();
        answerDao.save(answerOne);
        Answer answerTwo = exampleAnswer();
        answerDao.save(answerTwo);
        Answer answerThree = exampleAnswer();
        answerDao.save(answerThree);

        assertThat(answerDao.listAll())
                .extracting(Answer::getAnswerId)
                .contains(answerOne.getAnswerId(), answerTwo.getAnswerId(),
                        answerThree.getAnswerId());
    }

    public static Answer exampleAnswer() {
       Answer answer = new Answer();
       answer.setAnswerText(TestData.pickOne("Answer 1", "Answer 2", "Answer 3", "Answer 4"));
       answer.setQuestion_ID(TestData.pickOneLong(1, 2, 3, 4));
        return answer;

    }
}
