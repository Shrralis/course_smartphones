package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Standard extends Owner {
    @SuppressWarnings("unused")
    public Standard() {}

    public Standard(ResultSet from) {
        parse(from);
    }
    @Override
    public Owner parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
