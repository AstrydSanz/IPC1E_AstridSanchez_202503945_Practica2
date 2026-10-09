import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerY;
    private boolean arriba, abajo;
    
    // Vectores nativos obligatorios (CERO ArrayList)
    private Proyectil[] proyectiles;
    private int totalProyectiles;
    
    private Enemigo[] enemigos;
    private int totalEnemigos;
    
    private int puntaje;
    private Nave nave;
    private boolean juegoTerminado;

    public PanelJuego(Nave nave) {
        this.nave = nave;
        this.playerY = 250;
        this.puntaje = 0;
        this.juegoTerminado = false;
        
        this.proyectiles = new Proyectil[100];
        this.totalProyectiles = 0;
        
        this.enemigos = new Enemigo[20];
        this.totalEnemigos = 0;

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();
        
        // Generar enemigos iniciales
        generarEnemigosIniciales();
    }

    private void generarEnemigosIniciales() {
        if (totalEnemigos < enemigos.length) {
            enemigos[totalEnemigos] = new Enemigo(800, 100);
            totalEnemigos++;
        }
        if (totalEnemigos < enemigos.length) {
            enemigos[totalEnemigos] = new Enemigo(900, 300);
            totalEnemigos++;
        }
        if (totalEnemigos < enemigos.length) {
            enemigos[totalEnemigos] = new Enemigo(1000, 450);
            totalEnemigos++;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (juegoTerminado) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("¡GAME OVER / DERROTA!", 220, 250);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 18));
            g.drawString("Puntaje Final: " + puntaje, 330, 290);
            g.drawString("Cierra esta ventana para volver al menú.", 240, 330);
            return;
        }

        // Dibujar Nave del Jugador
        g.setColor(Color.CYAN);
        g.fillRect(50, playerY, 40, 25);
        
        // Información en pantalla
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Nave: " + nave.getTipoNave() + " | Puntaje: " + puntaje, 15, 20);

        // Dibujar Proyectiles
        g.setColor(Color.YELLOW);
        for (int i = 0; i < totalProyectiles; i++) {
            if (proyectiles[i] != null && proyectiles[i].isActivo()) {
                g.fillRect(proyectiles[i].getX(), proyectiles[i].getY(), 10, 4);
            }
        }

        // Dibujar Enemigos
        g.setColor(Color.RED);
        for (int i = 0; i < totalEnemigos; i++) {
            if (enemigos[i] != null && enemigos[i].isActivo()) {
                g.fillRect(enemigos[i].getX(), enemigos[i].getY(), 30, 30);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) return;

        // Movimiento de la nave
        if (arriba && playerY > 40) playerY -= 5;
        if (abajo && playerY < 500) playerY += 5;

        // Actualizar posiciones de proyectiles
        for (int i = 0; i < totalProyectiles; i++) {
            if (proyectiles[i] != null && proyectiles[i].isActivo()) {
                proyectiles[i].mover();
                if (proyectiles[i].getX() > 800) {
                    proyectiles[i].setActivo(false);
                }
            }
        }

        // Actualizar posiciones de enemigos y verificar colisiones
        for (int i = 0; i < totalEnemigos; i++) {
            if (enemigos[i] != null && enemigos[i].isActivo()) {
                enemigos[i].mover();

                // 1. Colisión entre Nave y Enemigo (Condición de Derrota)
                // Rectángulo del jugador: x=50, y=playerY, ancho=40, alto=25
                // Rectángulo del enemigo: x=enemigos[i].getX(), y=enemigos[i].getY(), ancho=30, alto=30
                if (50 < enemigos[i].getX() + 30 && 50 + 40 > enemigos[i].getX() &&
                    playerY < enemigos[i].getY() + 30 && playerY + 25 > enemigos[i].getY()) {
                    juegoTerminado = true;
                    timer.stop();
                }

                // Si el enemigo sale de la pantalla por la izquierda, lo reubicamos a la derecha
                if (enemigos[i].getX() < -30) {
                    enemigos[i].setX(800);
                }

                // 2. Colisión entre Proyectil y Enemigo
                for (int j = 0; j < totalProyectiles; j++) {
                    if (proyectiles[j] != null && proyectiles[j].isActivo()) {
                        int pX = proyectiles[j].getX();
                        int pY = proyectiles[j].getY();

                        if (pX > enemigos[i].getX() && pX < enemigos[i].getX() + 30 &&
                            pY > enemigos[i].getY() && pY < enemigos[i].getY() + 30) {
                            // ¡Impacto! Desactivar proyectil y destruir/reciclar enemigo
                            proyectiles[j].setActivo(false);
                            enemigos[i].setX(800); // Reaparece a la derecha
                            puntaje += 50; // Sumar puntos por destruir enemigo
                        }
                    }
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado) return;
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) arriba = true;
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) abajo = true;
        if (key == KeyEvent.VK_SPACE) {
            if (totalProyectiles < proyectiles.length) {
                proyectiles[totalProyectiles] = new Proyectil(90, playerY + 10);
                totalProyectiles++;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) arriba = false;
        if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) abajo = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}