package co.uniquindio.clinicaLaBienAmada.model;

public enum Especialidad {
    NEUROCIRUJANO("Neurocirujano"),
    ORTOPEDISTA("Ortopedista"),
    CARDIOLOGO("Cardiologo"),
    OTORRINOLARINGOLO("Otorrinolaringologo");
    //________________________________________________________________________________________________________________

    private String nombre;
    //________________________________________________________________________________________________________________

    Especialidad(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    //________________________________________________________________________________________________________________
}
