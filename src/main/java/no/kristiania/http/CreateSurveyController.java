package no.kristiania.http;

import no.kristiania.survey.Question;
import no.kristiania.survey.QuestionDao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Map;

public class CreateSurveyController implements HttpController{
    private final QuestionDao questionDao;

    public CreateSurveyController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
        Question question = new Question();
        question.setTitle(queryMap.get("fstittel"));
        question.setText(queryMap.get("fspm"));
        question.setAnswerOne(queryMap.get("answerEn"));
        question.setAnswerTwo(queryMap.get("answerTo"));
        question.setAnswerThree(queryMap.get("answerTre"));
        question.setSurvey_ID(Long.parseLong(queryMap.get("questions")));
        questionDao.save(question);




        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/createSurvey.html");
    }




}
