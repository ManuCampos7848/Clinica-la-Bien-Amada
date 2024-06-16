package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Medico extends Usuario implements Serializable {

    //____________________________________ Atributos ______________________________________________________
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    //_____________________________________________________________________________________________________

    //____________________________________ FK _____________________________________________________________
    @OneToMany(mappedBy = "medico")
    private List<Horario> horarios;

    @OneToMany(mappedBy = "medico")
    private List<Cita> citas;

    @OneToMany(mappedBy = "medico")
    private List<DiaLibre> diasLibres;
    //_____________________________________________________________________________________________________

}
