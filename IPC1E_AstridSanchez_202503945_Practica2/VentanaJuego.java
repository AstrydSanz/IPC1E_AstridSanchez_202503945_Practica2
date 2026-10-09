import javax.swing.JFrame;

public class VentanaJuego extends JFrame {
    
    public VentanaJuego(Piloto piloto, Nave nave) {
        setTitle("Quetzal Space Defender - Piloto: " + piloto.getNombre() + " [" + nave.getTipoNave() + "]");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Añadir el panel interactivo del juego
        PanelJuego panel = new PanelJuego(nave);
        add(panel);
    }
}