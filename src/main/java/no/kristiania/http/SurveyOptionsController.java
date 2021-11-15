package no.kristiania.http;


import no.kristiania.survey.Survey;
import no.kristiania.survey.SurveyDao;

import java.io.IOException;
import java.sql.SQLException;

public class SurveyOptionsController implements HttpController{

    private final SurveyDao surveyDao;

    public SurveyOptionsController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;

    }
    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        String messageBody = "";

        int value = 1;
        for (Survey survey : surveyDao.listAll()) {
            messageBody += "<option value=" + (value++) + ">" + survey.getTitle() + "</option>";
        }

        return new HttpMessage("HTTP/1.1 200 OK", messageBody);
    }
}
