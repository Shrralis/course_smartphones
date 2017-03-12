package models;

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
    @SuppressWarnings("unused")
    public Processor(ResultSet from) {
        parse(from);
    }
    @Override
    public Processor parse(ResultSet from) {
        super.parse(from);

        try {
            cores = from.getInt("cores");
            frequency = from.getDouble("frequency");
        } catch (SQLException ignored) {}
        return this;
    }
}
