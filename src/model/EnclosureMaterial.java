package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class EnclosureMaterial extends Owner {
    @SuppressWarnings("unused")
    public EnclosureMaterial() {}

    public EnclosureMaterial(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public EnclosureMaterial parse(ResultSet from) throws SQLException {
        super.parse(from);
        return this;
    }
}
