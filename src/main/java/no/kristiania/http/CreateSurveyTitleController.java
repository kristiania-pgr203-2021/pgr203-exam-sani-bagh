package no.kristiania.http;

import no.kristiania.survey.Survey;
import no.kristiania.survey.SurveyDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class CreateSurveyTitleController implements HttpController{
    private final SurveyDao surveyDao;

    public CreateSurveyTitleController(SurveyDao surveyDao) {
        this.surveyDao = surveyDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        Survey survey = new Survey();
        survey.setTitle(parameters.get("title"));
        surveyDao.save(survey);
        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/firstStepSurvey.html");
    }
}
