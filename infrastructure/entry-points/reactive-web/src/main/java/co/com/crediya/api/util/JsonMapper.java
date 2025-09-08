package co.com.crediya.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class JsonMapper {
    private ObjectMapper objectMapper = new ObjectMapper();

    public String convertirObjetoAJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
