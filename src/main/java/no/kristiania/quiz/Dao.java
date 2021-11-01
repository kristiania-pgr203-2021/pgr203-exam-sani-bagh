package no.kristiania.quiz;

import javax.sql.DataSource;

public abstract class Dao {

    public abstract Object retrieve(long id);
}
