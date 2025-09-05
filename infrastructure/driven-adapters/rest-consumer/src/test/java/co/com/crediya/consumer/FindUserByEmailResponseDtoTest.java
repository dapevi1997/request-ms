package co.com.crediya.consumer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class FindUserByEmailResponseDtoTest {

    @Test
    @DisplayName("Debe crear objeto usando constructor vacío y setters")
    void shouldCreateObjectWithNoArgsConstructorAndSetters() {
        FindUserByEmailResponseDto dto = new FindUserByEmailResponseDto();
        dto.setIdUser(1L);
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setEmail("juan.perez@example.com");
        dto.setBirthday(LocalDate.of(1990, 5, 20));
        dto.setAddress("Calle 123");
        dto.setDocumentId("123456789");
        dto.setPhone("3001234567");
        dto.setBaseSalary(new BigDecimal("2500.00"));
        dto.setIdRole(2L);

        assertThat(dto.getIdUser()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("Juan");
        assertThat(dto.getLastName()).isEqualTo("Perez");
        assertThat(dto.getEmail()).isEqualTo("juan.perez@example.com");
        assertThat(dto.getBirthday()).isEqualTo(LocalDate.of(1990, 5, 20));
        assertThat(dto.getAddress()).isEqualTo("Calle 123");
        assertThat(dto.getDocumentId()).isEqualTo("123456789");
        assertThat(dto.getPhone()).isEqualTo("3001234567");
        assertThat(dto.getBaseSalary()).isEqualByComparingTo("2500.00");
        assertThat(dto.getIdRole()).isEqualTo(2L);
    }

    @Test
    @DisplayName("Debe crear objeto usando constructor con argumentos")
    void shouldCreateObjectWithAllArgsConstructor() {
        LocalDate birthday = LocalDate.of(1985, 10, 15);
        BigDecimal salary = new BigDecimal("3500.00");

        FindUserByEmailResponseDto dto = new FindUserByEmailResponseDto(
                2L, "Maria", "Lopez", "maria.lopez@example.com",
                birthday, "Carrera 45", "987654321", "3017654321",
                salary, 3L
        );

        assertThat(dto.getIdUser()).isEqualTo(2L);
        assertThat(dto.getName()).isEqualTo("Maria");
        assertThat(dto.getLastName()).isEqualTo("Lopez");
        assertThat(dto.getEmail()).isEqualTo("maria.lopez@example.com");
        assertThat(dto.getBirthday()).isEqualTo(birthday);
        assertThat(dto.getAddress()).isEqualTo("Carrera 45");
        assertThat(dto.getDocumentId()).isEqualTo("987654321");
        assertThat(dto.getPhone()).isEqualTo("3017654321");
        assertThat(dto.getBaseSalary()).isEqualByComparingTo("3500.00");
        assertThat(dto.getIdRole()).isEqualTo(3L);
    }

    @Test
    @DisplayName("Debe crear objeto usando builder")
    void shouldCreateObjectWithBuilder() {
        LocalDate birthday = LocalDate.of(2000, 1, 1);
        BigDecimal salary = new BigDecimal("1500.00");

        FindUserByEmailResponseDto dto = FindUserByEmailResponseDto.builder()
                .idUser(3L)
                .name("Carlos")
                .lastName("Gomez")
                .email("carlos.gomez@example.com")
                .birthday(birthday)
                .address("Av 10 #20-30")
                .documentId("5555555")
                .phone("3025555555")
                .baseSalary(salary)
                .idRole(4L)
                .build();

        assertThat(dto.getIdUser()).isEqualTo(3L);
        assertThat(dto.getName()).isEqualTo("Carlos");
        assertThat(dto.getLastName()).isEqualTo("Gomez");
        assertThat(dto.getEmail()).isEqualTo("carlos.gomez@example.com");
        assertThat(dto.getBirthday()).isEqualTo(birthday);
        assertThat(dto.getAddress()).isEqualTo("Av 10 #20-30");
        assertThat(dto.getDocumentId()).isEqualTo("5555555");
        assertThat(dto.getPhone()).isEqualTo("3025555555");
        assertThat(dto.getBaseSalary()).isEqualByComparingTo("1500.00");
        assertThat(dto.getIdRole()).isEqualTo(4L);
    }

    @Test
    @DisplayName("Debe clonar objeto usando toBuilder")
    void shouldCloneObjectWithToBuilder() {
        FindUserByEmailResponseDto original = FindUserByEmailResponseDto.builder()
                .idUser(5L)
                .name("Laura")
                .lastName("Martinez")
                .email("laura.martinez@example.com")
                .baseSalary(new BigDecimal("4000.00"))
                .idRole(6L)
                .build();

        FindUserByEmailResponseDto clone = original.toBuilder()
                .email("laura.nueva@example.com")
                .build();

        assertThat(clone.getIdUser()).isEqualTo(5L);
        assertThat(clone.getName()).isEqualTo("Laura");
        assertThat(clone.getLastName()).isEqualTo("Martinez");
        assertThat(clone.getEmail()).isEqualTo("laura.nueva@example.com");
        assertThat(clone.getBaseSalary()).isEqualByComparingTo("4000.00");
        assertThat(clone.getIdRole()).isEqualTo(6L);
    }
}
