package no.kristiania.survey;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerAlternativesDaoTest {

    private AnswerAlternativesDao dao = new AnswerAlternativesDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedAnswerAlternative() throws SQLException {
        AnswerAlternatives answerAlternatives = exampleAnswerAlternatives();
        dao.save(answerAlternatives);

        assertThat(dao.retrieve(answerAlternatives.getAnswerId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(answerAlternatives);
    }


    public static AnswerAlternatives exampleAnswerAlternatives() {
        AnswerAlternatives answerAlternatives = new AnswerAlternatives();
        answerAlternatives.setAnswerText(TestData.pickOne("Question 1", "Question 2", "Question 3", "Question 4"));
        answerAlternatives.setQuestion_ID(TestData.pickOneLong(1, 2, 3, 4));

        return answerAlternatives;

    }
}
