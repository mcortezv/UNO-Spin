package interfaces;

public interface ISerializer {
    <T> T deserialize(String payload, Class<T> type);
    <T> String serialize(T obj);
}
