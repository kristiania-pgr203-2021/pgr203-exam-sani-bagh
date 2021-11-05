package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class Dao {

    private final DataSource dataSource;

    public Dao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract Object retrieve(long id) throws SQLException;

    public abstract Object readFromResultSet(ResultSet rs) throws SQLException;

}
