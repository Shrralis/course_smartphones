package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class BatteryType extends Owner {
    @SuppressWarnings("unused")
    public BatteryType() {}

    public BatteryType(ResultSet from) {
        parse(from);
    }
    @Override
    public BatteryType parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
