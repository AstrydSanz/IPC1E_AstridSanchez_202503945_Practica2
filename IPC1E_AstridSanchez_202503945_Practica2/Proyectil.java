public class Proyectil {
    private int x, y;
    private boolean activo;

    public Proyectil(int x, int y) {
        this.x = x;
        this.y = y;
        this.activo = true;
    }

    public void mover() {
        x += 8; // Velocidad de avance del proyectil hacia la derecha
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}