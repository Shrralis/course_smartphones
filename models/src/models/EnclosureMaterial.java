package models;

import java.sql.ResultSet;

/**
 * Created by shrralis on 2/19/17.
 */
public class EnclosureMaterial extends Owner {
    @SuppressWarnings("unused")
    EnclosureMaterial() {}
    @SuppressWarnings("unused")
    public EnclosureMaterial(ResultSet from) {
        parse(from);
    }
    @Override
    public EnclosureMaterial parse(ResultSet from) {
        super.parse(from);
        return this;
    }
}
