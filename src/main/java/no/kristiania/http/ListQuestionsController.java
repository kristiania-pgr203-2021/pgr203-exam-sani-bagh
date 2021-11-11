package no.kristiania.http;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListQuestionsController implements HttpController {

    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";


        for (Question question : questionDao.listAll()) {
            long id = question.getQuestionId();

            messageBody +=  "<h1 class='box'> Spørsmål " + id + ": " + question.getTitle() + "</h1>" +
                    "<h4 class='box'>" + question.getText() + "</h4>" +
                    "<label for ='one'>" +
                    "<input type=hidden name='questions_Id"+ id + "' value='" + id +"'> " +
                    "<label for='one'>" + "<input type='radio' id='one' name='answer" + id + "' value='" + question.getAnswerOne() + "'/>" + question.getAnswerOne() +  "</label><br>" +
                    "<label for='two'>" + "<input type='radio' id='two' name='answer" + id + "' value='" + question.getAnswerTwo() + "'/>" + question.getAnswerTwo() + "</label><br>" +
                    "<label for='three'>" + "<input type='radio' id='three' name='answer" + id + "' value='" + question.getAnswerThree() + "'/>" + question.getAnswerThree() +  "</label><br>" +
                    "</label>";
        }






        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
