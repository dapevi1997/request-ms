package co.com.crediya.model.mensajesender;

import co.com.crediya.model.exceptions.JsonMapperException;

public interface JsonMapperGateway {
    String objetoAJsonString(Object o) throws JsonMapperException;
}
