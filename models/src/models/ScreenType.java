package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class ScreenType extends Owner {
    @SuppressWarnings("unused")
    public ScreenType() {}

    public ScreenType(ResultSet from) {
        parse(from);
    }
    @Override
    public ScreenType parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
