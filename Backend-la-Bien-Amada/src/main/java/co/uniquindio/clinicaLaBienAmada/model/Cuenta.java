package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) - borrar para volver atras
@Inheritance(strategy = InheritanceType.JOINED )
public class  Cuenta implements Serializable {

    //__________________________ Atributos  y PK ____________________________________________
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  //  @EqualsAndHashCode.Include
    private int codigo;

    //@Column(nullable = false, unique = true, length = 40)
    @Email
    @Column(nullable = false)
    private String correo;

    @Column(nullable = false)
    private String password;


    //______________________________________________________________________________________


    //______________________________ FK ____________________________________________________
    @OneToMany(mappedBy = "cuenta")
    private List<Mensaje> mensajes;
    //_________________________________________________________________________________

    //_______________________ Constructor ____________________________________________
    public Cuenta (){}

    public Cuenta( String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    //_______________________________________________________________________________
}