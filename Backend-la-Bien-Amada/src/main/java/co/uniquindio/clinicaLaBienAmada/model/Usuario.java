package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//WENAS

//@Entity - Para volver atras
@Getter
@MappedSuperclass // Borrar por si las moscas
@Setter
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Usuario extends  Cuenta implements Serializable {

    //__________________________ Atributos y PK ____________________________________________
    @Column(nullable = false, length = 25, unique = true)
    private String cedula;
    @Column(nullable = false, length = 200)
    private String nombre;
    @Column(nullable = false, length = 20)
    private String telefono;
    @Lob
    @Column(nullable = false)
    private String urlFoto;

    @Enumerated(EnumType.STRING)
    private Ciudad ciudad;

    @Column(nullable = false)
    private boolean estado;

    public boolean isEstado() {
        return estado;
    }

    //_________________________________________________________________________________
    //_______________________ Constructor ____________________________________________
    public Usuario (){}
    //_______________________________________________________________________________

}
