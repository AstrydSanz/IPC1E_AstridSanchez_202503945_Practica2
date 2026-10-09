import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class GeneradorReportes {

    public static void exportarReporteHTML(Piloto[] pilotos, int totalPilotos, Partida[] partidas, int totalPartidas) {
        String nombreArchivo = "Reporte_QuetzalSpaceDefender.html";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html lang=\"es\">");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\">");
            writer.println("<title>Reporte - Quetzal Space Defender</title>");
            writer.println("<style>");
            writer.println("body { font-family: Arial, sans-serif; background-color: #0d1117; color: #c9d1d9; margin: 40px; }");
            writer.println("h1, h2 { color: #58a6ff; border-bottom: 1px solid #30363d; padding-bottom: 8px; }");
            writer.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; margin-bottom: 40px; }");
            writer.println("th, td { border: 1px solid #30363d; padding: 12px; text-align: left; }");
            writer.println("th { background-color: #161b22; color: #f0f6fc; }");
            writer.println("tr:nth-child(even) { background-color: #111418; }");
            writer.println(".container { background-color: #161b22; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); }");
            writer.println("</style>");
            writer.println("</head>");
            writer.println("<body>");
            
            writer.println("<div class=\"container\">");
            writer.println("<h1>Reporte General del Sistema</h1>");
            writer.println("<p><strong>Proyecto:</strong> Quetzal Space Defender - Práctica 2 (IPC1)</p>");
            
            // Sección de Pilotos Registrados
            writer.println("<h2>Pilotos Registrados (" + totalPilotos + ")</h2>");
            if (totalPilotos == 0) {
                writer.println("<p>No hay pilotos registrados en el sistema.</p>");
            } else {
                writer.println("<table>");
                writer.println("<tr><th>#</th><th>Nombre</th><th>Carnet</th></tr>");
                for (int i = 0; i < totalPilotos; i++) {
                    writer.println("<tr>");
                    writer.println("<td>" + (i + 1) + "</td>");
                    writer.println("<td>" + pilotos[i].getNombre() + "</td>");
                    writer.println("<td>" + pilotos[i].getCarnet() + "</td>");
                    writer.println("</tr>");
                }
                writer.println("</table>");
            }


            // Sección de Gráfica de Rendimiento / Puntajes
            writer.println("<h2>Gráfica de Puntajes</h2>");
            if (totalPartidas == 0) {
                writer.println("<p>No hay datos para graficar.</p>");
            } else {
                writer.println("<div style=\"background: #0d1117; padding: 20px; border: 1px solid #30363d; border-radius: 6px;\">");
                for (int i = 0; i < totalPartidas; i++) {
                    String piloto = partidas[i].getNombrePiloto();
                    int pts = partidas[i].getPuntaje();
                    // Calculamos un ancho relativo para la barra (máximo 400px o escala)
                    int anchoBarra = Math.min(pts * 2, 400); 
                    
                    writer.println("<div style=\"margin-bottom: 15px;\">");
                    writer.println("<span style=\"display: inline-block; width: 120px; font-weight: bold;\">" + piloto + " (" + pts + " pts)</span>");
                    writer.println("<div style=\"display: inline-block; background-color: #58a6ff; width: " + Math.max(anchoBarra, 10) + "px; height: 20px; border-radius: 4px; vertical-align: middle;\"></div>");
                    writer.println("</div>");
                }
                writer.println("</div>");
            }

            // Sección de Historial de Partidas
            writer.println("<h2>Historial de Partidas y Puntajes (" + totalPartidas + ")</h2>");
            if (totalPartidas == 0) {
                writer.println("<p>No hay registros de partidas jugadas.</p>");
            } else {
                writer.println("<table>");
                writer.println("<tr><th>#</th><th>Piloto</th><th>Modelo de Nave</th><th>Puntaje Obtenido</th></tr>");
                for (int i = 0; i < totalPartidas; i++) {
                    writer.println("<tr>");
                    writer.println("<td>" + (i + 1) + "</td>");
                    writer.println("<td>" + partidas[i].getNombrePiloto() + "</td>");
                    writer.println("<td>" + partidas[i].getTipoNave() + "</td>");
                    writer.println("<td><strong>" + partidas[i].getPuntaje() + "</strong></td>");
                    writer.println("</tr>");
                }
                writer.println("</table>");
            }

            writer.println("</div>");
            writer.println("</body>");
            writer.println("</html>");

            System.out.println("\n[Éxito] ¡Reporte HTML generado correctamente como '" + nombreArchivo + "' en la carpeta de tu proyecto!");

        } catch (IOException e) {
            System.out.println("\n[Error] No se pudo generar el archivo de reporte: " + e.getMessage());
        }
    }
}