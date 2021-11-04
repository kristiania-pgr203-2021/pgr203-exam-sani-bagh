package no.kristiania.survey;

import javax.sql.DataSource;
import java.sql.ResultSet;


public abstract class Dao {

    private final DataSource dataSource;

    protected Dao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public abstract Object retrieve(long id);

    public abstract Object readFromResultSet(ResultSet rs);

}
