public class ObjetoEspecial {
    private int x, y;
    private String tipo; // "energia", "asteroide", "capsula"
    private boolean activo;

    public ObjetoEspecial(int x, int y, String tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.activo = true;
    }

    public void mover() {
        x -= 2; // Se desplazan de derecha a izquierda como los obstáculos
        if (x < -40) {
            activo = false; // Desactivar si salen de la pantalla
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public String getTipo() { return tipo; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}