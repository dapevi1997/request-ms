package co.com.crediya.api.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolicitudesPathTest {

    @Test
    void testGettersAndSetters() {
        SolicitudesPath solicitudesPath = new SolicitudesPath();

        solicitudesPath.setCrearSolicitud("/crear");
        solicitudesPath.setSolicitudesRevision("/revision");

        assertThat(solicitudesPath.getCrearSolicitud()).isEqualTo("/crear");
        assertThat(solicitudesPath.getSolicitudesRevision()).isEqualTo("/revision");
    }
}
