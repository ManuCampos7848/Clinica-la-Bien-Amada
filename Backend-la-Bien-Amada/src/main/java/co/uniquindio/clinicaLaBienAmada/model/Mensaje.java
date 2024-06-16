package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mensaje implements Serializable {

    //__________________________ Atributos y PK ____________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Column(nullable = false)
    private LocalDate fechaCreacion;
    @Column(nullable = false)
    private String mensaje;
    //______________________________________________________________________________________

    //__________________________ FK ________________________________________________________
    @ManyToOne
    private Pqrs pqrs;
    @ManyToOne
    private Cuenta cuenta;
    //_________________________________________________________________________________

    //_______________________ Constructor ____________________________________________

}
