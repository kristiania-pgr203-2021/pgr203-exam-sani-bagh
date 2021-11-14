package no.kristiania.survey;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class QuestionDaoTest {

    private QuestionDao dao =new QuestionDao(TestData.testDataSource());


    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());



    @BeforeEach
    void initialSave() throws SQLException {
        for (int i=0; i<4; i++) {
            surveyDao.save(SurveyDaoTest.exampleSurvey());
        }
        for (int j=0; j<4; j++) {
           dao.save(exampleQuestion());
        }
    }

    @Test
    void shouldRetrieveSavedQuestion() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);

        assertThat(dao.retrieve(question.getQuestionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(question);
    }

    @Test
    void shouldRetrieveUpdatedQuestion() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);
        Question question1 = exampleUpdatedQuestion();
        dao.update(question1);

        assertThat(dao.retrieve(question1.getQuestionId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isNotEqualTo(question);

    }

    @Test
    void shouldListAllQuestions() throws SQLException {
        Question question = exampleQuestion();
        dao.save(question);
        Question questionTwo = exampleQuestion();
        dao.save(questionTwo);

        assertThat(dao.listAll())
                .extracting(Question::getQuestionId)
                .contains(question.getQuestionId(), questionTwo.getQuestionId());
    }

    @Test
    void shouldListQuestionsBySurveyId() throws SQLException {
        Question questionOne = exampleQuestion();
        questionOne.setSurvey_ID(2);
        dao.save(questionOne);

        Question questionTwo = exampleQuestion();
        questionTwo.setSurvey_ID(questionOne.getSurvey_ID());
        dao.save(questionTwo);

        Question nonMatchingQuestion = exampleQuestion();
        dao.save(nonMatchingQuestion);


        assertThat(dao.listQuestionBySurveyId(questionOne.getSurvey_ID()))
                .extracting(Question::getQuestionId)
                .contains(questionOne.getQuestionId(), questionTwo.getQuestionId())
                .doesNotContain(nonMatchingQuestion.getQuestionId());
    }


    public static Question exampleQuestion() {
        Question question = new Question();
        question.setTitle(TestData.pickOne("Title 1", "Title 2", "Title 3", "Title 4"));
        question.setText(TestData.pickOne("Question 1", "Question 2", "Question 3", "Question 4"));
        question.setAnswerOne(TestData.pickOne("Answer 1", "Answer 2", "Answer 3", "Answer 4"));
        question.setAnswerTwo(TestData.pickOne("Answer 1", "Answer 2", "Answer 3", "Answer 4"));
        question.setAnswerThree(TestData.pickOne("Answer 1", "Answer 2", "Answer 3", "Answer 4"));
        question.setSurvey_ID(TestData.pickOneLong(1, 1, 3, 4));

        return question;

    }

    public static Question exampleUpdatedQuestion() {
        Question question = new Question();
        question.setTitle(TestData.pickOne("New Title 1", "New Title 2", "New Title 3", "New Title 4"));
        question.setText(TestData.pickOne("New Question 1", "New Question 2", "New Question 3", "New Question 4"));
        question.setAnswerOne(TestData.pickOne("New Answer 1", "New Answer 2", "New Answer 3", "New Answer 4"));
        question.setAnswerTwo(TestData.pickOne("New Answer 1", "New Answer 2", "New Answer 3", "New Answer 4"));
        question.setAnswerThree(TestData.pickOne("New Answer 1", "New Answer 2", "New Answer 3", "New Answer 4"));

        question.setSurvey_ID(TestData.pickOneLong(3, 5, 6, 7));
        return question;

    }
}
