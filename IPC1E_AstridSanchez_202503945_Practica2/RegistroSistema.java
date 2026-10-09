public class RegistroSistema {
    // Vector nativo para almacenar pilotos (con capacidad máxima de 50 pilotos, por ejemplo)
    private Piloto[] listaPilotos;
    private int totalPilotos;

    // Constructor
    public RegistroSistema(int capacidadMaxima) {
        this.listaPilotos = new Piloto[defensivaCapacidad(capacidadMaxima)];
        this.totalPilotos = 0;
    }

    // Método auxiliar para asegurar una capacidad válida
    private int defensivaCapacidad(int cap) {
        if (cap <= 0) return 20;
        return cap;
    }

    // Método para agregar un nuevo piloto al vector
    public boolean registrarPiloto(String nombre, String carnet) {
        // Verificar si el vector está lleno
        if (totalPilotos >= listaPilotos.length) {
            System.out.println("[Error] El sistema de registro de pilotos está lleno.");
            return false;
        }

        // Verificar si ya existe un piloto con el mismo carnet
        if (buscarPiloto(carnet) != null) {
            System.out.println("[Error] Ya existe un piloto registrado con el carnet: " + carnet);
            return false;
        }

        // Crear e insertar en la siguiente posición libre del vector
        Piloto nuevoPiloto = new Piloto(nombre, carnet);
        listaPilotos[totalPilotos] = nuevoPiloto;
        totalPilotos++;
        
        System.out.println(">> ¡Piloto registrado con éxito!");
        return true;
    }

    // Método para buscar un piloto por su carnet recorriendo el vector
    public Piloto buscarPiloto(String carnet) {
        for (int i = 0; i < totalPilotos; i++) {
            if (listaPilotos[i].getCarnet().equalsIgnoreCase(carnet)) {
                return listaPilotos[i];
            }
        }
        return null; // No encontrado
    }

    // Método para listar todos los pilotos almacenados
    public void mostrarPilotos() {
        if (totalPilotos == 0) {
            System.out.println("\n[!] No hay pilotos registrados en el sistema.");
            return;
        }

        System.out.println("\n===== LISTA DE PILOTOS REGISTRADOS =====");
        for (int i = 0; i < totalPilotos; i++) {
            System.out.print((i + 1) + ". ");
            listaPilotos[i].mostrarInfo();
        }
    }
}