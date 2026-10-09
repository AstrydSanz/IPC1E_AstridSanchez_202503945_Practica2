public class RegistroSistema {
    private Piloto[] listaPilotos;
    private int totalPilotos;

    // Vector nativo para el historial de partidas (capacidad fija de 100 partidas)
    private Partida[] historialPartidas;
    private int totalPartidas;

    public RegistroSistema(int capacidadPilotos) {
        this.listaPilotos = new Piloto[capacidadPilotos];
        this.totalPilotos = 0;

        this.historialPartidas = new Partida[100];
        this.totalPartidas = 0;
    }

    public boolean registrarPiloto(String nombre, String carnet) {
        if (totalPilotos >= listaPilotos.length) {
            System.out.println("[Error] El sistema de registro de pilotos está lleno.");
            return false;
        }

        if (buscarPiloto(carnet) != null) {
            System.out.println("[Error] Ya existe un piloto registrado con el carnet: " + carnet);
            return false;
        }

        Piloto nuevoPiloto = new Piloto(nombre, carnet);
        listaPilotos[totalPilotos] = nuevoPiloto;
        totalPilotos++;
        
        System.out.println(">> ¡Piloto registrado con éxito!");
        return true;
    }

    public Piloto buscarPiloto(String carnet) {
        for (int i = 0; i < totalPilotos; i++) {
            if (listaPilotos[i].getCarnet().equalsIgnoreCase(carnet)) {
                return listaPilotos[i];
            }
        }
        return null;
    }

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

    // Registrar una nueva partida en el vector nativo
    public void registrarPartida(String nombrePiloto, String tipoNave, int puntaje) {
        if (totalPartidas < historialPartidas.length) {
            historialPartidas[totalPartidas] = new Partida(nombrePiloto, tipoNave, puntaje);
            totalPartidas++;
        }
    }

    // Mostrar Top de Puntajes (Historial ordenado de mayor a menor puntaje)
    public void mostrarTopPuntajes() {
        if (totalPartidas == 0) {
            System.out.println("\n[!] Aún no hay registros de partidas jugadas.");
            return;
        }

        // Creamos un arreglo temporal para ordenar sin alterar el vector original
        Partida[] temp = new Partida[totalPartidas];
        for (int i = 0; i < totalPartidas; i++) {
            temp[i] = historialPartidas[i];
        }

        // Ordenamiento burbuja descendente por puntaje
        for (int i = 0; i < totalPartidas - 1; i++) {
            for (int j = 0; j < totalPartidas - i - 1; j++) {
                if (temp[j].getPuntaje() < temp[j + 1].getPuntaje()) {
                    Partida aux = temp[j];
                    temp[j] = temp[j + 1];
                    temp[j + 1] = aux;
                }
            }
        }

        System.out.println("\n===== TOP DE PUNTAJES (HISTORIAL) =====");
        int limite = Math.min(10, totalPartidas); // Mostrar los mejores 10
        for (int i = 0; i < limite; i++) {
            System.out.print((i + 1) + ". ");
            temp[i].mostrarDetalle();
        }
    }
    // Getters necesarios para exportar reportes
    public Piloto[] getListaPilotos() {
        return listaPilotos;
    }

    public int getTotalPilotos() {
        return totalPilotos;
    }

    public Partida[] getHistorialPartidas() {
        return historialPartidas;
    }

    public int getTotalPartidas() {
        return totalPartidas;
    }
}