package co.com.crediya.api.util;

import co.com.crediya.api.dto.SolicitudDto;
import co.com.crediya.model.solicitud.Solicitud;
import co.com.crediya.model.tipoprestamo.TipoPrestamo;

public class CustomMapperReactiveWeb {
    public static SolicitudDto toSolicitudDto(Solicitud solicitud, TipoPrestamo tipoPrestamo) {
        SolicitudDto solicitudDto = new SolicitudDto();
        solicitudDto.setIdSolicitud(solicitud.getIdSolicitud());
        solicitudDto.setMonto(solicitud.getMonto());
        solicitudDto.setPlazo(solicitud.getPlazo());
        solicitudDto.setEmail(solicitud.getEmail());
        solicitudDto.setIdEstado(solicitud.getIdEstado());
        solicitudDto.setIdTipoPrestamo(solicitud.getIdTipoPrestamo());
        solicitudDto.setFechaCreacion(solicitud.getFechaCreacion());
        solicitudDto.setTasaMensual(tipoPrestamo.getTasaInteres());
        return solicitudDto;
    }
}
