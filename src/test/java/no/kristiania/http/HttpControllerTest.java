package no.kristiania.http;

import no.kristiania.survey.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpControllerTest {

    private final HttpServer server = new HttpServer(0);

    public HttpControllerTest() throws IOException {
    }


    private QuestionDao dao = new QuestionDao(TestData.testDataSource());


    private SurveyDao surveyDao = new SurveyDao(TestData.testDataSource());


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
        void shouldListAllSavedQuestions() throws SQLException, IOException {
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

        /*
        @Test
        void shouldListQuestionsWithAnswers() throws SQLException, IOException {
            AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/answerAndQuestion", new ListAllAnswersController(answerDao, questionDao));

            Question question = QuestionDaoTest.exampleQuestion();
            Question question2 = QuestionDaoTest.exampleQuestion();
            Answer answer = AnswerDaoTest.exampleAnswer();
            Answer answer2 = AnswerDaoTest.exampleAnswer();
            questionDao.save(question);
            questionDao.save(question2);
            answerDao.save(answer);
            answerDao.save(answer2);

            HttpClient client = new HttpClient("localhost", server.getPort(),"/api/answerAndQuestion");
            assertThat(client.getMessageBody())
                    .contains("<h1>" + question.getTitle() + "</h1>" +
                            "<h3>" + question.getText() + "</h3>" +
                            "<ul><li>" + answer.getAnswerText() + "</li></ul>")
                    .contains("<h1>" + question.getTitle() + "</h1>" +
                            "<h3>" + question.getText() + "</h3>" +
                            "<ul><li>" + answer.getAnswerText() + "</li></ul>");
        }

         */


        @Test
        void shouldListQuestionsForSurvey() throws SQLException, IOException {
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/showQuestions", new ListQuestionsForSurvey(questionDao));

            Question questionOne = QuestionDaoTest.exampleQuestion();
            Question questionTwo = QuestionDaoTest.exampleQuestion();
            questionDao.save(questionOne);
            questionDao.save(questionTwo);

            HttpClient client = new HttpClient("localhost", server.getPort(), "/api/showQuestions");

            assertThat(client.getMessageBody())
                    .contains("<h1 class='box'> Question " + questionOne.getQuestionId() + ": " + questionOne.getTitle() + "</h1>" +
                            "<h4 class='box'>" + questionOne.getText() + "</h4>" +
                            "<label for ='one'>" +
                            "<input type=hidden name='questions_Id" + questionOne.getQuestionId() + "' value='" + questionOne.getQuestionId() + "'> " +
                            "<label for='one'>" + "<input type='radio' id='one' name='answer" + questionOne.getQuestionId() + "' value='" + questionOne.getAnswerOne() + "'/>" + questionOne.getAnswerOne() + "</label><br>" +
                            "<label for='two'>" + "<input type='radio' id='two' name='answer" + questionOne.getQuestionId() + "' value='" + questionOne.getAnswerTwo() + "'/>" + questionOne.getAnswerTwo() + "</label><br>" +
                            "<label for='three'>" + "<input type='radio' id='three' name='answer" + questionOne.getQuestionId() + "' value='" + questionOne.getAnswerThree() + "'/>" + questionOne.getAnswerThree() + "</label><br>" +
                            "</label>")
                    .contains("<h1 class='box'> Question " + questionTwo.getQuestionId() + ": " + questionTwo.getTitle() + "</h1>" +
                            "<h4 class='box'>" + questionTwo.getText() + "</h4>" +
                            "<label for ='one'>" +
                            "<input type=hidden name='questions_Id" + questionTwo.getQuestionId() + "' value='" + questionTwo.getQuestionId() + "'> " +
                            "<label for='one'>" + "<input type='radio' id='one' name='answer" + questionTwo.getQuestionId() + "' value='" + questionTwo.getAnswerOne() + "'/>" + questionTwo.getAnswerOne() + "</label><br>" +
                            "<label for='two'>" + "<input type='radio' id='two' name='answer" + questionTwo.getQuestionId() + "' value='" + questionTwo.getAnswerTwo() + "'/>" + questionTwo.getAnswerTwo() + "</label><br>" +
                            "<label for='three'>" + "<input type='radio' id='three' name='answer" + questionTwo.getQuestionId() + "' value='" + questionTwo.getAnswerThree() + "'/>" + questionTwo.getAnswerThree() + "</label><br>" +
                            "</label>");
        }

        @Test
        void shouldCreateSurvey() throws IOException, SQLException {
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/createSurvey", new CreateSurveyController(questionDao));


            HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/createSurvey", "fstittel=Title+1&fspm=" +
                    "Text+1&answerEn=Answer+1&answerTo=Answer+2&answerTre=Answer+3&questions=1"
            );

            assertEquals(303, postClient.getStatusCode());

            assertThat(questionDao.listAll())
                    .anySatisfy(q -> {
                        assertThat(q.getTitle()).isEqualTo("Title 1");
                        assertThat(q.getText()).isEqualTo("Text 1");
                        assertThat(q.getAnswerOne()).isEqualTo("Answer 1");
                        assertThat(q.getAnswerTwo()).isEqualTo("Answer 2");
                        assertThat(q.getAnswerThree()).isEqualTo("Answer 3");
                        assertThat(q.getSurvey_ID()).isEqualTo(1);
                    });

        }

        @Test
        void shouldUpdateSurvey() throws IOException, SQLException {
            QuestionDao questionDao = new QuestionDao(TestData.testDataSource());
            server.addController("/api/updateSurvey", new UpdateSurveyConttroller(questionDao));

            HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/updateSurvey", "fstittel=New+Title&fspm=New+question&answerEn=New" +
                    "+Answer1&answerTo=New+Answer2&answerTre=New+Answer3&" +
                    "questionTitle=1");

            assertEquals(303, postClient.getStatusCode());

            assertThat(questionDao.listAll())
                    .anySatisfy(q -> {
                        assertThat(q.getTitle()).isEqualTo("New Title");
                        assertThat(q.getText()).isEqualTo("New question");
                        assertThat(q.getAnswerOne()).isEqualTo("New Answer1");
                        assertThat(q.getAnswerTwo()).isEqualTo("New Answer2");
                        assertThat(q.getAnswerThree()).isEqualTo("New Answer3");
                        assertThat(q.getSurvey_ID()).isEqualTo(1);
                    });
        }

        /*
        @Test
        void shouldSaveAnswers() throws IOException, SQLException {
            AnswerDao answerDao = new AnswerDao(TestData.testDataSource());
            server.addController("/api/saveAnswer", new SaveAnswerController(answerDao));

            HttpPostClient postClient = new HttpPostClient("localhost", server.getPort(), "/api/saveAnswer",
                    "answer=Saved+answer&questions_Id=1");

            assertEquals(303, postClient.getStatusCode());

            assertThat(answerDao.listAll())
                    .anySatisfy(a -> {
                        assertThat(a.getAnswerText()).isEqualTo("Saved answer");
                        assertThat(a.getQuestion_ID()).isEqualTo(1);
                    });


        }

         */
    }
}
