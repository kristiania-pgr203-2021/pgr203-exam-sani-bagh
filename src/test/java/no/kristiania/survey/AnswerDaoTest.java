package no.kristiania.survey;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerDaoTest {

    private AnswerDao dao = new AnswerDao(TestData.testDataSource());
    QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    QuestionDaoTest questionDaoTest = new QuestionDaoTest();


    @BeforeEach
    void initialSave() throws SQLException {
        for (int i=0; i<4; i++) {
            dao.save(exampleAnswerAlternatives());
        }
        for (int j=0; j<4; j++) {
            questionDao.save(questionDaoTest.exampleQuestion());
        }
    }

    @Test
    void shouldRetrieveSavedAnswerAlternative() throws SQLException {
        Answer answer = exampleAnswerAlternatives();
        dao.save(answer);

        assertThat(dao.retrieve(answer.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answer);
    }

    @Test
    void shouldListAllAnswerAlternatives() throws SQLException {
        Answer answer = exampleAnswerAlternatives();
        dao.save(answer);
        Answer answer1 = exampleAnswerAlternatives();
        dao.save(answer1);

        assertThat(dao.listAll())
                .extracting(Answer::getAnswerId)
                .contains(answer.getAnswerId(), answer1.getAnswerId());
    }

    public static Answer exampleAnswerAlternatives() {
        Answer answer = new Answer();
        answer.setAnswerText(TestData.pickOne("Question 1", "Question 2", "Question 3", "Question 4"));
        //answerAlternatives.setQuestion_ID(TestData.pickOneLong(1, 2, 3, 4));

        return answer;

    }
}
