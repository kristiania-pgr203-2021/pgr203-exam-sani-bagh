package no.kristiania.survey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SurveyDaoTest {

    private SurveyDao dao = new SurveyDao(TestData.testDataSource());
    QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
    QuestionDaoTest questionDaoTest = new QuestionDaoTest();
    AnswerAlternativesDaoTest answerAlternativesDaoTest = new AnswerAlternativesDaoTest();
    AnswerAlternativesDao answerAlternativesDao = new AnswerAlternativesDao(TestData.testDataSource());



    @BeforeEach
    void initialSave() throws SQLException {
        for (int i=0; i < 4; i++) {
            dao.save(exampleSurvey());
        }
        for (int k = 0; k < 4; k++) {
            questionDao.save(questionDaoTest.exampleQuestion());
        }
        for (int j=0; j < 4; j++) {
            answerAlternativesDao.save(answerAlternativesDaoTest.exampleAnswerAlternatives());
        }
    }



    @Test
    void shouldRetrieveSavedSurvey() throws SQLException {
        Survey survey = exampleSurvey();
        dao.save(survey);
        assertThat(dao.retrieve(survey.getId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(survey);
    }

    @Test
    void ShouldListAllSurveys() throws SQLException {
        Survey survey = exampleSurvey();
        dao.save(survey);
        Survey surveyTwo = exampleSurvey();
        dao.save(surveyTwo);

        assertThat(dao.listAll())
                .extracting(Survey::getId)
                .contains(survey.getId(), surveyTwo.getId());
    }


    public static Survey exampleSurvey() {
        Survey survey = new Survey();
        survey.setTitle(TestData.pickOne("Survey 1", "Survey 2", "Survey 3", "Survey 4"));
        survey.setQuestionId(TestData.pickOneLong(1, 2, 3, 4));
        survey.setAnswerId(TestData.pickOneLong(1, 2, 3, 4));

        return survey;
    }
}
