package interfaces;

public interface ISerializer {
    <T> String serialize(T obj);
    <T> T deserialize(String serialized, Class<T> clazz);
}
