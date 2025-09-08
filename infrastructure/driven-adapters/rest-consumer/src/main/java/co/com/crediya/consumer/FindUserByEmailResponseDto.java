package co.com.crediya.consumer;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FindUserByEmailResponseDto implements Serializable {
    private Long idUser;
    private String name;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String address;
    private String documentId;
    private String phone;
    private BigDecimal baseSalary;
    private Long idRole;
}
