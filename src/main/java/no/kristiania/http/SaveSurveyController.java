package no.kristiania.http;

import no.kristiania.survey.Survey;
import no.kristiania.survey.SurveyDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class SaveSurveyController implements HttpController{
    private final SurveyDao surveyDao;

    public SaveSurveyController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
            Map<String, String> queryMap = HttpMessage.parseRequestParameters(request.messageBody);
            Survey survey = new Survey();
            survey.setTitle(queryMap.get("title"));
            surveyDao.save(survey);

            String responseText = survey.getTitle();

            String responseTxt = "<p>Question: " + responseText + ", added" + "</p>";

            return new HttpMessage("HTTP/1.1 200 OK",  java.net.URLDecoder.decode(responseTxt, "UTF-8"), "text/html; charset=utf-8");
        }
    }

