package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

public class CreateQuestionController implements HttpController{
    private final QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao=questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();
        question.setTitle(parameters.get("title"));
        question.setText(parameters.get("text"));
        questionDao.save(question);

        return new HttpMessage("HTTP/1.1 200 OK", java.net.URLDecoder.decode("Question is saved", "UTF-8"), "text/html; charset=utf-8");
    }
}