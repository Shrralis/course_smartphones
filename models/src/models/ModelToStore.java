package models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 3/10/17.
 */
public class ModelToStore extends Owner {
    public Store store = null;
    public int model = 0;
    public double price = 0.0;

    public ModelToStore() {}

    public void setStore(Store store) {
        this.store = store;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    @Override
    public ModelToStore parse(ResultSet from, Connection connection) {
        super.parse(from);

        try {
            store = ParseUtils.parseViaReflection(new Store(), get("SELECT * FROM `store` WHERE `id` = "
                    + from.getInt("store"), connection));
            model = from.getInt("model");
            price = from.getDouble("price");
        } catch (SQLException ignored) {
            ignored.printStackTrace();
        }
        return this;
    }

    public ResultSet get(String sql, Connection connection) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }
}
