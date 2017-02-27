package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class MemoryCardType extends Owner {
    @SuppressWarnings("unused")
    public MemoryCardType() {}

    public MemoryCardType(ResultSet from) {
        parse(from);
    }
    @Override
    public MemoryCardType parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
