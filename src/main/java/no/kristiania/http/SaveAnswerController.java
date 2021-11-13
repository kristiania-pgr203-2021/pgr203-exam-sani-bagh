package no.kristiania.http;

import no.kristiania.survey.Answer;
import no.kristiania.survey.AnswerDao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

public class SaveAnswerController implements HttpController {
    private final AnswerDao answerDao;

    public SaveAnswerController(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);

        Answer answer = new Answer();

        for (Map.Entry<String, String> set : queryMap.entrySet()) {
            String id = set.getKey();
            String ans = set.getValue();

            if (id.startsWith("answer")) {
                answer.setAnswerText(ans);
                answer.setQuestion_ID(Integer.parseInt(id.replace("answer", "")));
                answerDao.save(answer);
            }
        }

        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/takeSurvey.html");
    }
}

