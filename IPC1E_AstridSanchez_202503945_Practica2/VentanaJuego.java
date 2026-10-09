import javax.swing.JFrame;

public class VentanaJuego extends JFrame {
    
    public VentanaJuego(Piloto piloto, Nave nave, RegistroSistema sistema) {
        setTitle("Quetzal Space Defender - Piloto: " + piloto.getNombre() + " [" + nave.getTipoNave() + "]");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelJuego panel = new PanelJuego(nave, piloto, sistema);
        add(panel);
    }
}