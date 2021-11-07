package no.kristiania.survey;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDaoTest {

    private UserDao dao = new UserDao(TestData.testDataSource());

    @Test
    void shouldRetrieveSavedUser() throws SQLException {
        User user = exampleUser();
        dao.save(user);

        assertThat(dao.retrieve(user.getUserId()))
                .hasNoNullFieldsOrProperties()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    void shouldListAllUsers() throws SQLException {
        User user = exampleUser();
        dao.save(user);
        User userTwo = exampleUser();
        dao.save(userTwo);

        assertThat(dao.listAll())
                .extracting(User::getUserId)
                .contains(user.getUserId(), userTwo.getUserId());
    }

    public static User exampleUser() {
        User user = new User();
        user.setFirstName(TestData.pickOne("ExampleFirstName 1", "ExampleFirstName 2", "ExampleFirstName 3", "ExampleFirstName 4"));
        user.setLastName(TestData.pickOne("ExampleLastName 1", "ExampleLastName 2", "ExampleLastName 3", "ExampleLastName 4"));
        user.setEmail(TestData.pickOne("example1@email", "example2@email", "example3@email", "example4@email"));
        return user;
    }
}


