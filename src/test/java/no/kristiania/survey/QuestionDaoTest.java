package no.kristiania.survey;


import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionDaoTest {

    private QuestionDao dao =new QuestionDao(TestData.testDataSource());


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
/*
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

 */

/*
        assertThat(dao.listAll(questionOne.getTitle()))
                .extracting(Question::getQuestionId)
                .contains(questionOne.getQuestionId(), questionTwo.getQuestionId())
                .doesNotContain(nonMatchingQuestion.getQuestionId());
    }

 */

    public static Question exampleQuestion() {
        Question question = new Question();
        question.setTitle(TestData.pickOne("Title 1", "Title 2", "Title 3", "Title 4"));
        question.setText(TestData.pickOne("Question 1", "Question 2", "Question 3", "Question 4"));

        return question;

    }
}
