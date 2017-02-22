package model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
