package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class BatteryType extends Owner {
    @SuppressWarnings("unused")
    public BatteryType() {}

    public BatteryType(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public BatteryType parse(ResultSet from) throws SQLException {
        super.parse(from);
        return this;
    }
}
