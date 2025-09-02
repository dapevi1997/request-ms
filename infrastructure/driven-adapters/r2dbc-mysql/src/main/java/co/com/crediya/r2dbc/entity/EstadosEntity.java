package co.com.crediya.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("estados")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EstadosEntity {

    @Id
    @Column("id_estado")
    private Long idEstado;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;
}
