package dominio;

public class Sucursal implements Comparable<Sucursal> {
    private String codigo;
    private String nombre;

    public Sucursal(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Sucursal() {
    }

    public Sucursal(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    @Override
    public String toString() {
        return this.nombre + ";" + this.codigo;
    }


    @Override
    public int compareTo(Sucursal o) {
        return this.codigo.compareTo(o.codigo);
    }
}
