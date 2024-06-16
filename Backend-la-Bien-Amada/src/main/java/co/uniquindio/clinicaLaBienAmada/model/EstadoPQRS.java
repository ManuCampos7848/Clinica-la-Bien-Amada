package co.uniquindio.clinicaLaBienAmada.model;

public enum EstadoPQRS {

    NUEVO("Nuevo"),
    EN_PROCESO("En Proceso"),
    RESUELTO("Resuelto"),
    ARCHIVADO("Archivado");
    //________________________________________________________________________________________________________________

    private String nombre;
    //________________________________________________________________________________________________________________

    EstadoPQRS(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    //________________________________________________________________________________________________________________
}
