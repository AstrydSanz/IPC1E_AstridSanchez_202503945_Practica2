import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        RegistroSistema sistema = new RegistroSistema(20);
        
        int opcion = 0;

        do {
            System.out.println("\n========================================");
            System.out.println("    QUETZAL SPACE DEFENDER - MENÚ       ");
            System.out.println("========================================");
            System.out.println("1. Jugar");
            System.out.println("2. Crear / Registrar Piloto");
            System.out.println("3. Top de Puntajes (Historial)");
            System.out.println("4. Generar Reporte HTML");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1:
                        System.out.println("\n--- CONFIGURACIÓN DE PARTIDA ---");
                        System.out.print("Ingrese el carnet del piloto registrado: ");
                        String carnetBusqueda = scanner.nextLine().trim();

                        Piloto pilotoEncontrado = sistema.buscarPiloto(carnetBusqueda);
                        if (pilotoEncontrado == null) {
                            System.out.println("[Error] No se encontró un piloto con ese carnet. Debe registrarse primero (Opción 2).");
                            break;
                        }

                        System.out.println("\nSeleccione el modelo de nave y dificultad:");
                        System.out.println("1. Explorador (Fácil - Disparo cada 2s)");
                        System.out.println("2. Caza Estelar (Normal - Disparo cada 1s)");
                        System.out.println("3. Acorazado (Difícil - Disparo rápido 0.3s)");
                        System.out.print("Elija una opción (1-3): ");

                        int opcionNave = solicitarEntero(scanner);
                        Nave naveSeleccionada = new Nave(opcionNave);

                        System.out.println("\n>> ¡Iniciando simulación gráfica para " + pilotoEncontrado.getNombre() + "!");
                        
                        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                new VentanaJuego(pilotoEncontrado, naveSeleccionada, sistema).setVisible(true);
                            }
                        });
                        break;

                    case 2:
                        System.out.println("\n--- REGISTRO DE NUEVO PILOTO ---");
                        System.out.print("Ingrese el nombre del piloto: ");
                        String nombre = scanner.nextLine().trim();
                        System.out.print("Ingrese el carnet del piloto: ");
                        String carnet = scanner.nextLine().trim();

                        sistema.registrarPiloto(nombre, carnet);
                        break;

                    case 3:
                        sistema.mostrarTopPuntajes();
                        break;

                    case 4:
                        System.out.println("\n--- GENERANDO REPORTE DEL SISTEMA ---");
                        GeneradorReportes.exportarReporteHTML(
                            sistema.getListaPilotos(), 
                            sistema.getTotalPilotos(), 
                            sistema.getHistorialPartidas(), 
                            sistema.getTotalPartidas()
                        );

                        break;
                        
                    case 5:    
                        System.out.println("\n¡Gracias por jugar Quetzal Space Defender! Saliendo del sistema...");
                        break;

                    default:
                        System.out.println("\n[Error] Opción no válida. Ingrese un número entre 1 y 4.");
                        break;
                }
            } else {
                System.out.println("\n[Error] Por favor, ingrese un valor numérico válido.");
                scanner.next();
            }

        } while (opcion != 5);

        scanner.close();
    }

    private static int solicitarEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("[Error] Ingrese un valor numérico válido: ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine();
        return val;
    }
}