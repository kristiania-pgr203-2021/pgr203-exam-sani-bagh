package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class UpdateQuestionsConttroller implements HttpController{
    private final QuestionDao questionDao;

    public UpdateQuestionsConttroller(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();
        question.setTitle(parameters.get("title"));
        question.setText(parameters.get("text"));
        questionDao.update(question);

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/newQuestionnaire.html");
    }
}
