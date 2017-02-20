package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Standard extends Owner {
    @SuppressWarnings("unused")
    public Standard() {}

    public Standard(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from) throws SQLException {
        super.parse(from);
        return this;
    }
}
