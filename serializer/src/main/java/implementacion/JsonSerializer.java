package implementacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import interfaces.ISerializer;

import java.io.IOException;

public class JsonSerializer implements ISerializer {

    private final ObjectMapper objectMapper;

    public JsonSerializer() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public <T> String serialize(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al serializar objeto a JSON", e);
        }
    }

    @Override
    public <T> T deserialize(String payload, Class<T> type) {
        try {
            return objectMapper.readValue(payload, type);
        } catch (IOException e) {
            throw new RuntimeException("Error al deserializar JSON", e);
        }
    }
}
