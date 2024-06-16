package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Pqrs implements Serializable {


    //__________________________ Atributos y PK ____________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Column(nullable = false)
    private LocalDate fecha_Creacion;
    @Column(nullable = false)
    private String tipo;
    @Column(nullable = false)
    private String motivo;
   // @Column(nullable = false)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPQRS estadoPQRS;


    // _________________________ FK ________________________________________________________
    @ManyToOne
    private Cita cita;

    @OneToMany(mappedBy = "pqrs")
    private List<Mensaje> mensajes;

}
