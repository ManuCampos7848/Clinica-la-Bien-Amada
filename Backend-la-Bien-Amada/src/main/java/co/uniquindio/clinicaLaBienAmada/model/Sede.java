package co.uniquindio.clinicaLaBienAmada.model;

public enum Sede {

    // SEDES
    GRYFFINDOR("Gryffindor"),
    HUFFLEPUFF(""),
    RAVENCLAW(""),
    SLYTHERIN("Slytherin"),
    LA_CUEVA_DEL_HUMO("La Cueva del Humo");

    private String nombre;

    Sede(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){return nombre;}
}
