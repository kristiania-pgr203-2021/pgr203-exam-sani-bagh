package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class TakeSurveyController implements HttpController{

    private final QuestionDao questionDao;

    public TakeSurveyController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";

        for (Question question : questionDao.listAll()) {
            long id = question.getQuestionId();
            messageBody +=  "<h1 class='box'> Spørsmål " + id + ": " + question.getTitle() + "</h1>" + "<h4 class='box'>" + question.getText() + "</h4>" +
                    "<input type=hidden name='questionId' value=" + id + "> " +
                    "<input type='radio' id='one' name='answer' value='" + question.getAnswerOne() + "'/> <label for='one'>" + question.getAnswerOne() + "</label><br>" +
                    "<input type='radio' id='two' name='answer' value='" + question.getAnswerTwo() + "'/> <label for='two'>" + question.getAnswerTwo() + "</label><br>" +
                    "<input type='radio' id='three' name='answer' value='" + question.getAnswerThree() + "'/> <label for='three'>" + question.getAnswerThree() + "</label><br>";
        }


        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
