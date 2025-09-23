package co.com.crediya.r2dbc;

import co.com.crediya.r2dbc.dto.SolicitudPendienteAprobacionDto;
import co.com.crediya.r2dbc.entity.SolicitudEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SolicitudReactiveRepository extends ReactiveCrudRepository<SolicitudEntity, Long>, ReactiveQueryByExampleExecutor<SolicitudEntity> {
    @Query("""
    SELECT 
        s.email AS email,
        s.monto AS monto,
        s.plazo AS plazo,
        s.fecha_creacion AS fecha_creacion,
        COALESCE(aprobadas.total_aprobado, 0) AS total_ultimos_30_dias,
        tp.nombre AS nombre_tipo_prestamo,
        tp.tasa_interes AS tasa_interes,
        e.nombre AS nombre_estado
    FROM solicitud s
    INNER JOIN estados e ON s.id_estado = e.id_estado
    INNER JOIN tipo_prestamo tp ON s.id_tipo_prestamo = tp.id_tipo_prestamo
    LEFT JOIN (
        SELECT 
            s2.email,
            SUM(s2.monto) AS total_aprobado
        FROM solicitud s2
        INNER JOIN estados e2 ON s2.id_estado = e2.id_estado
        WHERE e2.nombre = 'APROBADA'
          AND s2.fecha_creacion >= NOW() - INTERVAL 30 DAY
        GROUP BY s2.email
    ) aprobadas ON s.email = aprobadas.email
    WHERE e.nombre = :estado
    ORDER BY s.fecha_creacion DESC
    LIMIT :limit OFFSET :offset
    """)
    Flux<SolicitudPendienteAprobacionDto> findByEstadoFilter(String estado, int limit, int offset);

    Flux<SolicitudEntity> findAllByEmail(String email);
}
