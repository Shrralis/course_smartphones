package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class represents owner of some object.
 *
 * Created by shrralis on 2/19/17.
 */
public class Owner extends Model implements Identifiable {
    public int id = 0;

    public String name = null;

    /**
     * Creates an owner with empty ID.
     */
    public Owner() {}

    public Owner(ResultSet from) {
        parse(from);
    }
    /**
     * Fills an owner from JSONObject
     */
    public Owner parse(ResultSet from) {
        try {
            id = from.getInt("id");
            name = from.getString("name");
        } catch (SQLException ignored) {}
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
