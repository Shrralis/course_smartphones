package model;

import java.io.Serializable;
import java.sql.*;
import java.util.HashMap;

/**
 * Корінний клас для усіх моделей.
 *
 * Модель дозволяє зберігати певний об'єкт усередині як тег.
 *
 * Created by shrralis on 2/19/17.
 */
public abstract class Model implements Serializable {
    /**
     * The model's tag.
     */
    private Object mTag;
    /**
     * Map used to store model's tags.
     */
    private HashMap<Integer, Object> mKeyedTags;

    /**
     * Creates empty model
     */
    public Model() {}
    /**
     * Returns this model's tag.
     *
     * @return the Object stored in this model as a tag
     *
     * @see #setTag(Object)
     * @see #getTag(int)
     */
    public Object getTag() {
        return mTag;
    }
    /**
     * Sets the tag associated with this model. A tag can be used to store
     * data within a model without resorting to another data structure.
     *
     * @param tag an Object to tag the model with
     *
     * @see #getTag()
     * @see #setTag(int, Object)
     */
    public void setTag(Object tag) {
        mTag = tag;
    }

    /**
     * Returns the tag associated with this model and the specified key.
     *
     * @param key The key identifying the tag
     *
     * @return the Object stored in this model as a tag
     *
     * @see #setTag(int, Object)
     * @see #getTag()
     */
    public Object getTag(int key) {
        if (mKeyedTags != null) return mKeyedTags.get(key);
        return null;
    }
    /**
     * Sets a tag associated with this model and a key. A tag can be used
     * to store data within a model without resorting to another
     * data structure.
     *
     * @see #setTag(Object)
     * @see #getTag(int)
     */
    public void setTag(int key, final Object tag) {
        if (mKeyedTags == null) {
            mKeyedTags = new HashMap<>(2);
        }
        mKeyedTags.put(key, tag);
    }

    public Model(ResultSet from) throws SQLException {
        parse(from);
    }
    /**
     * Parses object from source.
     * @param response server API object.
     * @return this object.
     * @throws SQLException if any critical error occurred while parsing.
     */
    public Model parse(ResultSet response) throws SQLException {
        return ParseUtils.parseViaReflection(this, response);
    }
    /**
     * Parses object from source.
     * @param response server API object.
     * @return this object.
     * @throws SQLException if any critical error occurred while parsing.
     */
    public Model parse(ResultSet response, Connection connection) throws SQLException {
        return ParseUtils.parseViaReflection(this, response);
    }
}
