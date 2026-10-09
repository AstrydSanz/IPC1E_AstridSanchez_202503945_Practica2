public class Enemigo {
    private int x, y;
    private boolean activo;

    public Enemigo(int x, int y) {
        this.x = x;
        this.y = y;
        this.activo = true;
    }

    public void mover() {
        x -= 3; // Velocidad de desplazamiento hacia la izquierda
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
}