package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Manufacturer extends Owner {
    String country = null;

    @SuppressWarnings("unused")
    public Manufacturer() {}

    public Manufacturer(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public Manufacturer parse(ResultSet from) throws SQLException {
        super.parse(from);

        country = from.getString("country");

        return this;
    }
}
