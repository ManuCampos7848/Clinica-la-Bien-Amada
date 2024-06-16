package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Cita implements Serializable {

    //___________________________________ Atributos y PK ______________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
   // @Column(nullable = false)
   // private LocalDateTime fechaCreacion;
    @Column(nullable = false)
    private LocalDate fechaCita;
    @Column(nullable = false)
    private String horaCita;
    @Column(nullable = false)
    private String motivo;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoCita estadoCita;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sede sede;
    //______________________________________________________________________________________

    //__________________________________ FK ________________________________________________
    @ManyToOne
    private Paciente paciente;
    @ManyToOne
    private Medico medico;
    @OneToOne(mappedBy = "cita")
    private Atencion atencion;
    @OneToMany(mappedBy = "cita")
    private List<Pqrs> pqrs;
    //______________________________________________________________________________________

}
