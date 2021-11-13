package no.kristiania.http;

import no.kristiania.survey.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControllerTest {

    private final HttpServer server = new HttpServer(0);

    public ControllerTest() throws IOException {
    }


    private QuestionDao dao = new QuestionDao(TestData.testDataSource());


    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());


    @Test
    void shouldReturnSurveyTitleAsDropdownOptions() throws SQLException, IOException {
        SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
        Survey survey = new Survey();
        Survey survey1 = new Survey();
        Survey survey2 = new Survey();
        survey.setTitle("Survey title 1");
        survey1.setTitle("Survey title 2");
        survey2.setTitle("Survey title 3");
        surveyDao.save(survey);
        surveyDao.save(survey1);
        surveyDao.save(survey2);

        server.addController("/api/surveyOptions", new SurveyOptionsController(surveyDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/surveyOptions");

        assertEquals("<option value=1>Survey title 1</option><option value=2>Survey title 2</option>" +
                        "<option value=3>Survey title 3</option>",
                client.getMessageBody());
    }

    @Test
    void shouldReturnQuestionTitleAsDropdownOption() throws SQLException, IOException {
        QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
        Question question1 = new Question();
        question1.setTitle("Question title 1");
        question1.setText("Text 1");
        question1.setAnswerOne("Answer 1");
        question1.setAnswerTwo("Answer 2");
        question1.setAnswerThree("Answer 3");
        question1.setSurvey_ID(1);
        questionDao.save(question1);
        Question question2 = new Question();
        question2.setTitle("Question title 2");
        question2.setText("Text 2");
        question2.setAnswerOne("Answer 1");
        question2.setAnswerTwo("Answer 2");
        question2.setAnswerThree("Answer 3");
        question2.setSurvey_ID(2);
        questionDao.save(question2);
        Question question3 = new Question();
        question3.setTitle("Question title 3");
        question3.setText("Text 3");
        question3.setAnswerOne("Answer 1");
        question3.setAnswerTwo("Answer 2");
        question3.setAnswerThree("Answer 3");
        question3.setSurvey_ID(3);
        questionDao.save(question3);

        server.addController("/api/surveyList", new QuestionOptionController(questionDao));

        HttpClient client = new HttpClient("localhost", server.getPort(), "/api/surveyList");

        assertEquals("<option value=1>Question title 1</option><option value=2>Question title 2</option>" +
                        "<option value=3>Question title 3</option>",
                client.getMessageBody());
    }

    @Nested
    class testWithInitSave {

        @BeforeEach
        void initialSave() throws SQLException {
            for (int i = 0; i < 4; i++) {
                surveyDao.save(SurveyDaoTest.exampleSurvey());
            }
            for (int j = 0; j < 4; j++) {
                dao.save(QuestionDaoTest.exampleQuestion());
            }
        }


        @Test
        void shouldListQuestions() throws SQLException, IOException {
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/questions", new ListAllSavedQuestionsController(questionDao));

            Question question1 = QuestionDaoTest.exampleQuestion();
            Question question2 = QuestionDaoTest.exampleQuestion();
            questionDao.save(question1);
            questionDao.save(question2);

            HttpClient client = new HttpClient("localhost", server.getPort(), "/api/questions");
            assertThat(client.getMessageBody())
                    .contains("<h4>" + question1.getQuestionId() + ":" + question1.getTitle() + "</h4>" + "\n" + "<h4> Question: " + question1.getText() + "</h4>" + "\n" +
                            "<h4> Answer one: " + question1.getAnswerOne() + "</h4>" + "\n" +
                            "<h4>Answer two: " + question1.getAnswerTwo() + "</h4>" + "\n" +
                            "<h4>Answer three: " + question1.getAnswerThree() + "</h4>")
                    .contains("<h4>" + question2.getQuestionId() + ":" + question2.getTitle() + "</h4>" + "\n" + "<h4> Question: " + question2.getText() + "</h4>" + "\n" +
                            "<h4> Answer one: " + question2.getAnswerOne() + "</h4>" + "\n" +
                            "<h4>Answer two: " + question2.getAnswerTwo() + "</h4>" + "\n" +
                            "<h4>Answer three: " + question2.getAnswerThree() + "</h4>")
            ;


        }

        @Test
        void shouldCreateSurvey() throws IOException, SQLException {
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/createSurvey", new CreateSurveyController(questionDao));


            HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/createSurvey", "fstittel=Title1&fspm=" +
                    "Text1&answerEn=Answer1&answerTo=Answer2&answerTre=Answer3&questions=1"
            );

            assertEquals(303, postClient.getStatusCode());

            assertThat(questionDao.listAll())
                    .anySatisfy(q -> {
                        assertThat(q.getTitle()).isEqualTo("Title1");
                        assertThat(q.getText()).isEqualTo("Text1");
                        assertThat(q.getAnswerOne()).isEqualTo("Answer1");
                        assertThat(q.getAnswerTwo()).isEqualTo("Answer2");
                        assertThat(q.getAnswerThree()).isEqualTo("Answer3");
                        assertThat(q.getSurvey_ID()).isEqualTo(1);
                    });


        }

        @Test
        void shouldCreateSurveyTitle() throws IOException, SQLException {
            SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());
            server.addController("/api/surveys", new CreateSurveyTitleController(surveyDao));

            HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/surveys", "title=Title1"
            );

            assertEquals(303, postClient.getStatusCode());
            assertThat(surveyDao.listAll())
                    .anySatisfy(s -> {
                        assertThat(s.getTitle()).isEqualTo("Title1");

                    });

        }
}
}
