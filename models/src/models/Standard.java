package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class Standard extends Owner {
    @SuppressWarnings("unused")
    Standard() {}
    @SuppressWarnings("unused")
    public Standard(ResultSet from) {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
