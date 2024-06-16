package co.uniquindio.clinicaLaBienAmada.model;

public enum Eps {
    SANITAS("Sanitas Contributivo"),
    ASMETSALUD("AsmetSalud"),
    MEDIMAS("Medimas"),
    NUEVAEPS("Nueva-EPS");

    private String nombre;

    Eps(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
