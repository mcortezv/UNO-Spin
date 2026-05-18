package interfaces;

/**
 * The interface Serializer.
 */
public interface ISerializer {

    /**
     * Deserialize t.
     *
     * @param <T>     the type parameter
     * @param payload the payload
     * @param type    the type
     * @return the t
     */
    <T> T deserialize(String payload, Class<T> type);

    /**
     * Serialize string.
     *
     * @param <T> the type parameter
     * @param obj the obj
     * @return the string
     */
    <T> String serialize(T obj);
}
