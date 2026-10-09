public class Nave {
    private String tipoNave;    // Explorador, Caza Estelar o Acorazado
    private String dificultad;  // Fácil, Normal o Difícil
    private int tiempoDisparo;  // Milisegundos para el sleep() del disparo

    // Constructor para inicializar la nave según la opción elegida
    public Nave(int opcionDificultad) {
        switch (opcionDificultad) {
            case 1:
                this.tipoNave = "Explorador";
                this.dificultad = "Fácil";
                this.tiempoDisparo = 2000; // 2 segundos de recarga[cite: 4]
                break;
            case 2:
                this.tipoNave = "Caza Estelar";
                this.dificultad = "Normal";
                this.tiempoDisparo = 1000; // 1 segundo de cadencia[cite: 4]
                break;
            case 3:
                this.tipoNave = "Difícil (Acorazado)";
                this.dificultad = "Difícil";
                this.tiempoDisparo = 300;  // 0.3 segundos (300 ms) ráfagas rápidas[cite: 4]
                break;
            default:
                this.tipoNave = "Caza Estelar";
                this.dificultad = "Normal";
                this.tiempoDisparo = 1000;
                break;
        }
    }
    
    // Getters
    public String getTipoNave() {
        return tipoNave;
    }

    public String getDificultad() {
        return dificultad;
    }

    public int getTiempoDisparo() {
        return tiempoDisparo;
    }

    // Método para mostrar las características de la nave
    public void mostrarDetalles() {
        System.out.println("Nave: " + tipoNave + " | Dificultad: " + dificultad + " | Cadencia de disparo: " + tiempoDisparo + "ms");
    }
}