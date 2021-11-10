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
    void shouldListQuestionsByTitle() throws SQLException {
        Question questionOne = exampleQuestion();
        questionOne.setTitle("Example title");
        dao.save(questionOne);

        Question questionTwo = exampleQuestion();
        questionTwo.setTitle(questionOne.getTitle());
        dao.save(questionTwo);

        Question nonMatchingQuestion = exampleQuestion();
        dao.save(nonMatchingQuestion);


        assertThat(dao.listQuestionByTitle(questionOne.getTitle()))
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
        question.setSurvey_ID(TestData.pickOneLong(1, 2, 3, 4));

        return question;

    }
}
