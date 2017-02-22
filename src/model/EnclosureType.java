package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class EnclosureType extends Owner {
    @SuppressWarnings("unused")
    public EnclosureType() {}

    public EnclosureType(ResultSet from) {
        parse(from);
    }
    @Override
    public EnclosureType parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
