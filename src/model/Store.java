package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Store extends Owner {
    public String link = null;
    @SuppressWarnings("unused")
    public Store() {}

    public Store(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public Store parse(ResultSet from) throws SQLException {
        super.parse(from);

        link = from.getString("link");

        return this;
    }
}
