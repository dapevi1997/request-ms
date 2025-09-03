package co.com.crediya.usecase.obtenerlistadorevisionmanual;

import co.com.crediya.model.estado.gateways.EstadosRepository;
import co.com.crediya.model.logger.LoggerGateway;
import co.com.crediya.model.solicitud.SolicitudConTotalAprobadoUltimoMes;
import co.com.crediya.model.solicitud.gateways.SolicitudRepository;
import co.com.crediya.model.tipoprestamo.gateways.TipoPrestamoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ObtenerListadoRevisionManualUseCaseTest {
    @Mock
    private SolicitudRepository solicitudRepository;

    @Mock
    private TipoPrestamoRepository tipoPrestamoRepository;

    @Mock
    private LoggerGateway loggerGateway;

    @Mock
    private EstadosRepository estadosRepository;

    @InjectMocks
    private ObtenerListadoRevisionManualUseCase useCase;

    @Test
    void obtenerListadoRevisionManualUseCaseTest() {
        // Arrange
        SolicitudConTotalAprobadoUltimoMes solicitud1 = new SolicitudConTotalAprobadoUltimoMes();
        when(solicitudRepository.findByEstadoFilter(anyString(), anyInt(), anyInt()))
                .thenReturn(Flux.just(solicitud1));

        StepVerifier.create(useCase.listSolicitudesAprobadasUltimoMes("Estado", 10, 0))
                .expectComplete();

    }

}