package co.uniquindio.clinicaLaBienAmada.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Administrador extends Cuenta implements Serializable  {
    @Column(nullable = false)
    private String nombre;
    //________________________________________________________________________________________________________________
}
