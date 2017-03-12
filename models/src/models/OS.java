package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class OS extends Owner {
    @SuppressWarnings("unused")
    OS() {}
    @SuppressWarnings("unused")
    public OS(ResultSet from) {
        parse(from);
    }
    @Override
    public OS parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
