package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class SimCardType extends Owner {
    @SuppressWarnings("unused")
    public SimCardType() {}

    public SimCardType(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public SimCardType parse(ResultSet from) throws SQLException {
        super.parse(from);
        return this;
    }
}
