package dominio;

public class Conexion implements Comparable<Conexion> {
    private String codigoSucursal1;
    private String codigoSucursal2;
    private int latencia;
    private boolean existe;

    public Conexion(String codigoSucursal1, String codigoSucursal2, int latencia, boolean existe) {
        this.codigoSucursal1 = codigoSucursal1;
        this.codigoSucursal2 = codigoSucursal2;
        this.latencia = latencia;
        this.existe = existe;
    }

    public Conexion() {
        this.existe = false;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setLatencia(int latencia) {
        this.latencia = latencia;
    }

    public int getLatencia() {
        return latencia;
    }

    @Override
    public int compareTo(Conexion o) {
        return 0;
    }

}
