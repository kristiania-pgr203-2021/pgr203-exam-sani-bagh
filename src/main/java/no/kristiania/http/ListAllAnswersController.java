package no.kristiania.http;

import no.kristiania.survey.Answer;
import no.kristiania.survey.AnswerDao;
import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;


import java.io.IOException;
import java.sql.SQLException;

public class ListAllAnswersController implements HttpController {

    private QuestionDao questionDao;
    private AnswerDao answerDao;

    public ListAllAnswersController(AnswerDao answerDao) {
        this.answerDao=answerDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";
        for (Answer answer : answerDao.listAll()) {
            long id = answer.getAnswerId();
            messageBody += "<h4>" + id + ":" + answer.getAnswerText() + "</h4>" + "\n" + "<h4> Question: " + answer.getQuestion_ID() + "</h4>";




        }
        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }

}
