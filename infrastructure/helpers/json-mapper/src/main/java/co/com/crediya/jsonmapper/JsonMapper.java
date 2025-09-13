package co.com.crediya.jsonmapper;

import co.com.crediya.model.exceptions.JsonMapperException;
import co.com.crediya.model.mensajesender.JsonMapperGateway;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JsonMapper implements JsonMapperGateway {
    private final ObjectMapper objectMapper;

    @Override
    public String objetoAJsonString(Object o) throws JsonMapperException {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new JsonMapperException(e.getMessage());
        }
    }
}
