package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class UserDao extends AbsractDao<User>{
    protected UserDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected User readFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getLong("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email"));
        return user;
    }

    public void save(User user) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(
                    "insert into user (first_name, last_name, email) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS

            )) {
                statement.setString(1, user.getFirstName());
                statement.setString(2, user.getLastName());
                statement.setString(3, user.getEmail());

                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    user.setUserId(rs.getLong("user_id"));
                }
            }
        }
    }

    @Override
    public List<User> listAll() throws SQLException {
        return listAllWithPreparedStatement("select * from user");
    }


    @Override
    public User retrieve(long id) throws SQLException {
        return retrieveAbstract("select * from user where user_id = ?", id );
    }

}
