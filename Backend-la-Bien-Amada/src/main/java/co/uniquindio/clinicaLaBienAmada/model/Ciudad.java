package co.uniquindio.clinicaLaBienAmada.model;

public enum Ciudad {

    ARMENIA("Armenia"),
    GONDOR("Gondor"),
    MEDELLIN("Medellin"),
    CARTAGENA("Cartagena"),
    ESGAROTH("Esgaroth"),
    DRAENOR("Draenor"),
    KHAZAD_DUM("Khazad Dum"),
    MORDOR("Mordor");

    private String nombre;

    Ciudad(String nombre){
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
}
