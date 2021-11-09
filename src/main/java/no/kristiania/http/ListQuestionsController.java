package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class ListQuestionsController implements HttpController{

    private final QuestionDao questionDao;

    public ListQuestionsController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";


        for (Question q : questionDao.listAll()) {
            messageBody += "<h4>" + q.getQuestionId()  + "</h4>"  + "\n" +
                            "<h4>" + q.getTitle()  + "</h4>"  + "\n"
                            + "<h4>" + q.getText() + "</h4>";

        }
        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
