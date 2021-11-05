package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class QuestionOptionsController implements HttpController{

    private final QuestionDao questionDao;

    public QuestionOptionsController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String responseText = "";

        int value = 1;
        for (Question question: questionDao.listQuestionText()) {
            responseText += "<option value=" + (value++) + ">" + question + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", responseText);
    }
}
