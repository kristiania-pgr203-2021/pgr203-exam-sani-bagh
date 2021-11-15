package no.kristiania.http;


import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;
import no.kristiania.survey.Survey;

import java.io.IOException;
import java.sql.SQLException;

public class QuestionOptionController implements HttpController{

    private final QuestionDao questionDao;



    public QuestionOptionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";

        int value = 1;
        for (Question question : questionDao.listAll()) {
            messageBody += "<option value=" + (value++) + ">" + question.getTitle() + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}

