package dominio;

public class Equipo implements Comparable<Equipo> {
    private String nombre;
    private String manager;
    private ArbolABBGenerico jugadoresDelEquipo;

    public Equipo(String nombre, String manager) {
        this.nombre = nombre;
        this.manager = manager;
        this.jugadoresDelEquipo = new ArbolABBGenerico();
    }

    public ArbolABBGenerico getJugadoresDelEquipo() {
        return jugadoresDelEquipo;
    }

    public Equipo(String nombre) {
        this.nombre = nombre;
    }

    public int cantJugadores() {
        return jugadoresDelEquipo.cantidad();
    }

    @Override
    public String toString() {
        return nombre + ';' + manager + ';' + jugadoresDelEquipo.cantidad();
    }

    @Override
    public int compareTo(Equipo o) {
        return this.nombre.compareTo(o.nombre);
    }
}
