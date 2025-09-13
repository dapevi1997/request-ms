package co.com.crediya.model.utils;

public class Constantes {

    public Constantes() {
        throw new UnsupportedOperationException();
    }

    public static final String MENSAJE_ESTADO_NO_ENCONTRADO = "No se encuentra estado con el id proporcionado ";
    public static final String ESTADO_APROBADO = "APROBADO";


    public static class ColasSqs{
        public static final String COLA_NOTIFICACION_ESTADO = "colaNotificacionEstado";
        public static final String COLA_CAPACIDAD_ENDEUDAMIENTO = "colaCapacidadEndeudamiento";
        public static final String COLA_APROBADOS = "colaAprobados";
    }
}
