package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Processor extends Owner {
    public int cores;

    public double frequency;
    @SuppressWarnings("unused")
    public Processor() {}

    public Processor(ResultSet from) throws SQLException {
        parse(from);
    }
    @Override
    public Processor parse(ResultSet from) throws SQLException {
        super.parse(from);

        cores = from.getInt("cores");
        frequency = from.getDouble("frequency");

        return this;
    }
}
