package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbsractDao<T> {
    protected final DataSource dataSource;

    public AbsractDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }



    protected T retrieveAbstract(String sql, long id) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setLong(1, id);
                try (ResultSet rs = statement.executeQuery()) {
                    if (rs.next()) {
                        return readFromResultSet(rs);
                    }else {
                        return null;
                    }

                }
            }
        }
    }




    public void saveAndUpdateWithStatement(T object, String sql) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS
            )) {
                setStatement(object, statement);


                statement.executeUpdate();

                try (ResultSet rs = statement.getGeneratedKeys()) {
                    rs.next();
                    setGeneratedKeyStatement(object, rs);
                }
            }
        }
    }

    protected abstract void setGeneratedKeyStatement(T object, ResultSet rs) throws SQLException;



    protected abstract T readFromResultSet(ResultSet rs) throws SQLException;

    protected abstract void setStatement(T object, PreparedStatement statement) throws SQLException;

    public abstract List<T> listAll() throws SQLException;


    protected List<T> listAllWithPreparedStatement(String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                try (ResultSet rs = statement.executeQuery()) {
                    ArrayList<T> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(readFromResultSet(rs));
                    }
                    return result;
                }
            }
        }
    }

    public abstract T retrieve(long id) throws SQLException;
}












