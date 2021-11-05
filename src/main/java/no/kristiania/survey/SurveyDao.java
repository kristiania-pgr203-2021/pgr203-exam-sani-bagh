package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyDao {

    private final DataSource dataSource;

    public SurveyDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

        public List<Survey> listAll() throws SQLException {

            try (Connection connection = dataSource.getConnection()) {
                try (PreparedStatement statement = connection.prepareStatement("select * from survey")) {
                    try (ResultSet rs = statement.executeQuery()) {
                        ArrayList<Survey> result = new ArrayList<>();
                        while (rs.next()) {
                            result.add(readFromResultSet(rs));
                        }
                        return result;
                    }
                }
            }
        }

    public void save(Survey survey) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into survey(title) values (?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, survey.getTitle());


                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    survey.setId(rs.getLong("id"));
                }
            }
        }
    }




    private Survey readFromResultSet(ResultSet rs) throws SQLException {
    Survey survey = new Survey();
    survey.setId(rs.getLong("id"));
    survey.setTitle(rs.getString("title"));

        return survey;
    }
}
