public class Partida {
    private String nombrePiloto;
    private String tipoNave;
    private int puntaje;

    public Partida(String nombrePiloto, String tipoNave, int puntaje) {
        this.nombrePiloto = nombrePiloto;
        this.tipoNave = tipoNave;
        this.puntaje = puntaje;
    }

    public String getNombrePiloto() {
        return nombrePiloto;
    }

    public String getTipoNave() {
        return tipoNave;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void mostrarDetalle() {
        System.out.println("Piloto: " + nombrePiloto + " | Nave: " + tipoNave + " | Puntaje: " + puntaje);
    }
}