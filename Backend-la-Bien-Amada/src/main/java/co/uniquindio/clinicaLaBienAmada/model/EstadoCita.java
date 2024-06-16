package co.uniquindio.clinicaLaBienAmada.model;

public enum EstadoCita {
    PROGRAMADA("Programada"),
    COMPLETADA("Completada"),
    CANCELADA("Cancelada");
    //________________________________________________________________________________________________________________

    private String nombre;
    //________________________________________________________________________________________________________________

    EstadoCita(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    //________________________________________________________________________________________________________________
}
