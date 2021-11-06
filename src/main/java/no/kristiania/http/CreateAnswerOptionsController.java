package no.kristiania.http;

import no.kristiania.survey.AnswerAlternatives;
import no.kristiania.survey.AnswerAlternativesDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CreateAnswerOptionsController implements HttpController{
    private final AnswerAlternativesDao answerAlternativesDao;

    public CreateAnswerOptionsController(AnswerAlternativesDao answerAlternativesDao) {
        this.answerAlternativesDao=answerAlternativesDao;
    }
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        AnswerAlternatives answerAlternatives = new AnswerAlternatives();
        answerAlternatives.setAnswerText(parameters.get("answerText"));
        answerAlternatives.setQuestion_ID(Long.parseLong(parameters.get("question_ID")));
        answerAlternativesDao.save(answerAlternatives);


        return new HttpMessage("HTTP/1.1 200 OK", java.net.URLDecoder.decode("Answer is saved is saved", "UTF-8"), "text/html; charset=utf-8");
    }
}
