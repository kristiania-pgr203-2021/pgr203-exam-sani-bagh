package no.kristiania.survey;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerAlternativesDaoTest {

    private AnswerAlternativesDao dao = new AnswerAlternativesDao(TestData.testDataSource());
    QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    QuestionDaoTest questionDaoTest = new QuestionDaoTest();


    @BeforeEach
    void initSave() throws SQLException {
        for (int i=0; i<4; i++) {
            dao.save(exampleAnswerAlternatives());
        }
        for (int j=0; j<10; j++) {
            questionDao.save(questionDaoTest.exampleQuestion());
        }
    }

    @Test
    void shouldRetrieveSavedAnswerAlternative() throws SQLException {
        AnswerAlternatives answerAlternatives = exampleAnswerAlternatives();
        dao.save(answerAlternatives);

        assertThat(dao.retrieve(answerAlternatives.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answerAlternatives);
    }

    @Test
    void shouldListAllAnswerAlternatives() throws SQLException {
        AnswerAlternatives answerAlternatives = exampleAnswerAlternatives();
        dao.save(answerAlternatives);
        AnswerAlternatives answerAlternatives1 = exampleAnswerAlternatives();
        dao.save(answerAlternatives1);

        assertThat(dao.listAll())
                .extracting(AnswerAlternatives::getAnswerId)
                .contains(answerAlternatives.getAnswerId(), answerAlternatives1.getAnswerId());
    }

    public static AnswerAlternatives exampleAnswerAlternatives() {
        AnswerAlternatives answerAlternatives = new AnswerAlternatives();
        answerAlternatives.setAnswerText(TestData.pickOne("Question 1", "Question 2", "Question 3", "Question 4"));
        answerAlternatives.setQuestion_ID(TestData.pickOneLong(1, 2, 3, 4));

        return answerAlternatives;

    }
}
