package no.kristiania.survey;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class SurveySurveyUserDaoTest {

    private SurveyUserDao dao = new SurveyUserDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedUser() throws SQLException {
        SurveyUser surveyUser = exampleUser();
        dao.save(surveyUser);

        assertThat(dao.retrieve(surveyUser.getUserId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(surveyUser);
    }

    @Test
    void shouldListAllUsers() throws SQLException {
        SurveyUser surveyUser = exampleUser();
        dao.save(surveyUser);
        SurveyUser surveyUserTwo = exampleUser();
        dao.save(surveyUserTwo);

        assertThat(dao.listAll())
                .extracting(SurveyUser::getUserId)
                .contains(surveyUser.getUserId(), surveyUserTwo.getUserId());
    }

    public static SurveyUser exampleUser() {
        SurveyUser surveyUser = new SurveyUser();
        surveyUser.setFirstName(TestData.pickOne("ExampleFirstName 1", "ExampleFirstName 2", "ExampleFirstName 3", "ExampleFirstName 4"));
        surveyUser.setLastName(TestData.pickOne("ExampleLastName 1", "ExampleLastName 2", "ExampleLastName 3", "ExampleLastName 4"));
        surveyUser.setEmail(TestData.pickOne("example1@email", "example2@email", "example3@email", "example4@email"));
        return surveyUser;
    }
}


