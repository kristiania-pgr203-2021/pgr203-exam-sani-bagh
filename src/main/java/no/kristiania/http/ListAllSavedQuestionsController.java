package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListAllSavedQuestionsController implements HttpController{

    private QuestionDao questionDao;

    public ListAllSavedQuestionsController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";
        for (Question question : questionDao.listAll()) {
            long id = question.getQuestionId();
            messageBody += "<h4>" + id + ":" + question.getTitle() + "</h4>" + "\n" + "<h4> Question: " + question.getText() + "</h4>" + "\n" +
                    "<h4> Answer one: " + question.getAnswerOne() + "</h4>" + "\n" +
                    "<h4>Answer two: " + question.getAnswerTwo() + "</h4>" + "\n" +
                    "<h4>Answer three: " + question.getAnswerThree() + "</h4>";


        }
        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
