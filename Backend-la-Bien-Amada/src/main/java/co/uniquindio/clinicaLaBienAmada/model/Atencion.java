package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Atencion implements Serializable {

    //______________________________ Atributos y PK _________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int codigo;
    @Column(nullable = false, length = 800)
    private String diagnostico;
    @Column(nullable = false, length = 800)
    private String tratamiento;
    @Column(nullable = false, length = 600)
    private String notasMedicas;
    //______________________________________________________________________________________


    //________________________________ FK __________________________________________________
    @OneToOne
    private Cita cita;
    //______________________________________________________________________________________

    //___________________________________ Metodo Constructor _______________________________
    public Atencion(){}
    //______________________________________________________________________________________
}
