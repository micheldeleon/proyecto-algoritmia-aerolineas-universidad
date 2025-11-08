package dominio;

import interfaz.Categoria;

public class Jugador implements Comparable<Jugador> {
    private String alias;
    private String nombre;
    private String apellido;
    private Categoria categoria;
    private boolean tieneEquipo;

    public Jugador(String alias, String nombre, String apellido, Categoria categoria) {
        this.alias = alias;
        this.nombre = nombre;
        this.apellido = apellido;
        this.categoria = categoria;
        this.tieneEquipo = false;
    }

    public Jugador(String alias) {
        this.alias = alias;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean tieneEquipo() {
        return tieneEquipo;
    }

    public void setTieneEquipo(boolean tieneEquipo) {
        this.tieneEquipo = tieneEquipo;
    }

    @Override
    public String toString() {
        return alias + ';' + nombre + ';' + apellido + ';' + categoria;
    }

    @Override
    public int compareTo(Jugador o) {
        return this.alias.compareTo(o.alias);
    }
}
