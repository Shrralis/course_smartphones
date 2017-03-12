package models;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by shrralis on 2/19/17.
 */
public class Store extends Owner {
    public String link = null;
    private ModelToStore modelToStore = null;
    @SuppressWarnings("unused")
    public Store() {}
    @SuppressWarnings("unused")
    public Store(ResultSet from) {
        parse(from);
    }
    @Override
    public Store parse(ResultSet from) {
        super.parse(from);

        try {
            link = from.getString("link");
        } catch (SQLException ignored) {}
        return this;
    }

    public String getLink() {
        return link;
    }

    public ModelToStore getModelToStore() {
        return modelToStore;
    }

    public void setModelToStore(ModelToStore modelToStore) {
        this.modelToStore = modelToStore;
    }
}
