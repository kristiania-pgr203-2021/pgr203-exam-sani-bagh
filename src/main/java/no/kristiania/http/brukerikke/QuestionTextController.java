package no.kristiania.http.brukerikke;

import no.kristiania.http.HttpController;
import no.kristiania.http.HttpMessage;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;

public class QuestionTextController implements HttpController {
    private final QuestionDao questionDao;

    public QuestionTextController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";

        int value = 1;
        for (String s: questionDao.listQuestionText()) {
            messageBody += "<option value=" + (value++) + ">" + s + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
