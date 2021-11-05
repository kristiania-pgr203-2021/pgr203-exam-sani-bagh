package no.kristiania.http;

import no.kristiania.survey.Survey;
import no.kristiania.survey.SurveyDao;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

public class RetrieveSurveysController implements HttpController {

    private final SurveyDao surveyDao;

    public RetrieveSurveysController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }


    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, UnsupportedEncodingException {
        String responseText = "";


        for (Survey s : surveyDao.listAll()) {
            responseText += "<h3>" + s.getTitle() + ">" + "</h3>";
        }
        return new HttpMessage("HTTP/1.1 200 OK", java.net.URLDecoder.decode(responseText, "UTF-8"), "text/html; charset=utf-8");
    }
}
