public class Piloto {
    // Atributos del piloto
    private String nombre;
    private String carnet;
    private int mejorPuntaje;

    // Constructor para inicializar el piloto
    public Piloto(String nombre, String carnet) {
        this.nombre = nombre;
        this.carnet = carnet;
        this.mejorPuntaje = 0; // Empieza con 0 puntos
    }

    // Métodos Getters y Setters para acceder y modificar los datos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public int getMejorPuntaje() {
        return mejorPuntaje;
    }

    public void setMejorPuntaje(int mejorPuntaje) {
        this.mejorPuntaje = mejorPuntaje;
    }

    // Método para mostrar la información del piloto de forma ordenada
    public void mostrarInfo() {
        System.out.println("Piloto: " + nombre + " | Carnet: " + carnet + " | Mejor Puntaje: " + mejorPuntaje);
    }
}