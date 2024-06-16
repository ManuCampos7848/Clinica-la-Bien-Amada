package co.uniquindio.clinicaLaBienAmada.model;

public enum TipoDeSangre {
    A_POSITIVO("A+"),
    A_NEGATIVO("A-"),
    AB_POSITIVO("AB+"),
    ABNEGATIVO("AB-"),
    O_POSITIVO("O+"),
    O_NEGATIVO("O-"),
    B_POSITIVO("B+"),
    B_NEGATIVO("B-");
    //________________________________________________________________________________________________________________

    private String nombre;
    //________________________________________________________________________________________________________________

    TipoDeSangre(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){return nombre;}
    //________________________________________________________________________________________________________________
}
