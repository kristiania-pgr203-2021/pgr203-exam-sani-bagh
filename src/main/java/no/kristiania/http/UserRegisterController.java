package no.kristiania.http;

import no.kristiania.survey.SurveyUser;
import no.kristiania.survey.SurveyUserDao;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class UserRegisterController implements HttpController{
    private final SurveyUserDao surveyUserDao;

    public UserRegisterController(SurveyUserDao surveyUserDao) {
        this.surveyUserDao = surveyUserDao;
    }

    @Override
    public HttpMessage handle(HttpMessage request) throws SQLException, IOException {
        Map<String, String> parameters = HttpMessage.parseRequestParameters(request.messageBody);
        SurveyUser user = new SurveyUser();
        user.setFirstName(parameters.get("first_name"));
        user.setLastName(parameters.get("last_name"));
        user.setEmail(parameters.get("email"));
        surveyUserDao.save(user);
        return new HttpMessage("HTTP/1.1 303 See Other", "Location", "/takeSurvey.html");
    }
}
