package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class MemoryCardType extends Owner {
    @SuppressWarnings("unused")
    public MemoryCardType() {}

    public MemoryCardType(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public MemoryCardType parse(ResultSet from) throws SQLException {
        super.parse(from);
        return this;
    }
}
