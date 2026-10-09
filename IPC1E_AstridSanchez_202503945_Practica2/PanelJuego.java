import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PanelJuego extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private int playerY;
    private boolean arriba, abajo;
    
    // Vectores nativos obligatorios (CERO ArrayList)
    private Proyectil[] proyectiles;
    private int totalProyectiles;
    
    private Enemigo[] enemigos;
    private int totalEnemigos;

    private ObjetoEspecial[] especiales;
    private int totalEspeciales;
    
    private int puntaje;
    private Nave nave;
    private Piloto piloto;
    private RegistroSistema sistema;
    private boolean juegoTerminado;
    private boolean partidaRegistrada;
    private int tiempoRalentizado; // Contador para el efecto del asteroide (2 segundos)
    private Random random;

    public PanelJuego(Nave nave, Piloto piloto, RegistroSistema sistema) {
        this.nave = nave;
        this.piloto = piloto;
        this.sistema = sistema;
        this.playerY = 250;
        this.puntaje = 0;
        this.juegoTerminado = false;
        this.partidaRegistrada = false;
        this.tiempoRalentizado = 0;
        this.random = new Random();
        
        this.proyectiles = new Proyectil[100];
        this.totalProyectiles = 0;
        
        this.enemigos = new Enemigo[20];
        this.totalEnemigos = 0;

        this.especiales = new ObjetoEspecial[10];
        this.totalEspeciales = 0;

        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        timer = new Timer(20, this);
        timer.start();
        
        generarElementosIniciales();
    }

    private void generarElementosIniciales() {
        if (totalEnemigos < enemigos.length) {
            enemigos[totalEnemigos] = new Enemigo(800, 100);
            totalEnemigos++;
        }
        if (totalEnemigos < enemigos.length) {
            enemigos[totalEnemigos] = new Enemigo(900, 350);
            totalEnemigos++;
        }
        // Generar elementos especiales iniciales
        if (totalEspeciales < especiales.length) {
            especiales[totalEspeciales] = new ObjetoEspecial(1000, 200, "energia");
            totalEspeciales++;
        }
        if (totalEspeciales < especiales.length) {
            especiales[totalEspeciales] = new ObjetoEspecial(1200, 450, "asteroide");
            totalEspeciales++;
        }
        if (totalEspeciales < especiales.length) {
            especiales[totalEspeciales] = new ObjetoEspecial(1400, 150, "capsula");
            totalEspeciales++;
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
        g.setColor(tiempoRalentizado > 0 ? Color.ORANGE : Color.CYAN);
        g.fillRect(50, playerY, 40, 25);
        
        // Información en pantalla
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 14));
        g.drawString("Nave: " + nave.getTipoNave() + " | Puntaje: " + puntaje, 15, 20);
        if (tiempoRalentizado > 0) {
            g.setColor(Color.YELLOW);
            g.drawString("[!] ¡BLOQUEADO POR ASTEROIDE!", 350, 20);
        }

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

        // Dibujar Objetos Especiales
        for (int i = 0; i < totalEspeciales; i++) {
            if (especiales[i] != null && especiales[i].isActivo()) {
                String tipo = especiales[i].getTipo();
                if (tipo.equals("energia")) {
                    g.setColor(Color.GREEN);
                    g.fillOval(especiales[i].getX(), especiales[i].getY(), 25, 25);
                } else if (tipo.equals("asteroide")) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRoundRect(especiales[i].getX(), especiales[i].getY(), 30, 30, 10, 10);
                } else if (tipo.equals("capsula")) {
                    g.setColor(Color.MAGENTA);
                    g.fillRect(especiales[i].getX(), especiales[i].getY(), 20, 20);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) return;

        if (tiempoRalentizado > 0) {
            tiempoRalentizado--;
        } else {
            if (arriba && playerY > 40) playerY -= 5;
            if (abajo && playerY < 500) playerY += 5;
        }

        for (int i = 0; i < totalProyectiles; i++) {
            if (proyectiles[i] != null && proyectiles[i].isActivo()) {
                proyectiles[i].mover();
                if (proyectiles[i].getX() > 800) {
                    proyectiles[i].setActivo(false);
                }
            }
        }

        for (int i = 0; i < totalEnemigos; i++) {
            if (enemigos[i] != null && enemigos[i].isActivo()) {
                enemigos[i].mover();

                // Colisión Nave - Enemigo (Derrota)
                if (50 < enemigos[i].getX() + 30 && 50 + 40 > enemigos[i].getX() &&
                    playerY < enemigos[i].getY() + 30 && playerY + 25 > enemigos[i].getY()) {
                    juegoTerminado = true;
                    
                    if (!partidaRegistrada) {
                        sistema.registrarPartida(piloto.getNombre(), nave.getTipoNave(), puntaje);
                        partidaRegistrada = true;
                    }
                    
                    timer.stop();
                }

                if (enemigos[i].getX() < -30) {
                    enemigos[i].setX(800);
                }

                for (int j = 0; j < totalProyectiles; j++) {
                    if (proyectiles[j] != null && proyectiles[j].isActivo()) {
                        int pX = proyectiles[j].getX();
                        int pY = proyectiles[j].getY();

                        if (pX > enemigos[i].getX() && pX < enemigos[i].getX() + 30 &&
                            pY > enemigos[i].getY() && pY < enemigos[i].getY() + 30) {
                            proyectiles[j].setActivo(false);
                            enemigos[i].setX(800);
                            puntaje += 50;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < totalEspeciales; i++) {
            if (especiales[i] != null && especiales[i].isActivo()) {
                especiales[i].mover();

                if (50 < especiales[i].getX() + 30 && 50 + 40 > especiales[i].getX() &&
                    playerY < especiales[i].getY() + 30 && playerY + 25 > especiales[i].getY()) {
                    
                    String tipo = especiales[i].getTipo();
                    if (tipo.equals("energia")) {
                        puntaje += 150;
                        for (int eIdx = 0; eIdx < totalEnemigos; eIdx++) {
                            if (enemigos[eIdx] != null) enemigos[eIdx].setX(800);
                        }
                    } else if (tipo.equals("asteroide")) {
                        tiempoRalentizado = 100;
                    } else if (tipo.equals("capsula")) {
                        puntaje += 10;
                    }
                    
                    especiales[i].setActivo(false);
                }

                if (!especiales[i].isActivo()) {
                    especiales[i] = new ObjetoEspecial(800 + random.nextInt(400), random.nextInt(450) + 40, 
                        random.nextBoolean() ? (random.nextBoolean() ? "energia" : "capsula") : "asteroide");
                }
            }
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (juegoTerminado || tiempoRalentizado > 0) return;
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