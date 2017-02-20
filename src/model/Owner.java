package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents owner of some object.
 *
 * Created by shrralis on 2/19/17.
 */
public class Owner extends Model implements Identifiable {
    public int id;

    public String name;

    /**
     * Creates an owner with empty ID.
     */
    public Owner() {}

    public Owner(ResultSet from) throws SQLException {
        parse(from);
    }
    /**
     * Fills an owner from JSONObject
     */
    public Owner parse(ResultSet from) throws SQLException {
        fields = from;
        id = from.getInt("id");
        name = from.getString("name");

        return this;
    }
    /**
     * Creates according with given ID.
     */
    public Owner(int id) {
        this.id = id;
    }
    @Override
    public int getId() {
        return id;
    }
}
