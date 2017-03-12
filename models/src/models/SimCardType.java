package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class SimCardType extends Owner {
    @SuppressWarnings("unused")
    SimCardType() {}
    @SuppressWarnings("unused")
    public SimCardType(ResultSet from) {
        parse(from);
    }
    @Override
    public SimCardType parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
