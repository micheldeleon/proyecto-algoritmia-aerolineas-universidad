package dominio;

public class ListaEnlazada<T extends Comparable<T>> {
    private NodoGenerico<T> cabeza;

    public ListaEnlazada() {
        this.cabeza = null;
    }

    public int cantidad() {
        int cant = 0;
        NodoGenerico<T> actual = cabeza;
        while (actual != null) {
            cant++;
            actual = actual.getSiguiente();
        }
        return cant;
    }

    public void agregarInicio(T n) {
        NodoGenerico<T> nuevo = new NodoGenerico<>(n);
        nuevo.setSiguiente(this.cabeza);
        this.cabeza = nuevo;
    }

    public void eliminarAlInicio() {
        if (cabeza != null) {
            cabeza = cabeza.getSiguiente();
        }
    }

    public boolean esVacia() {
        return this.cabeza == null;
    }

    public NodoGenerico<T> getCabeza() {
        return cabeza;
    }

    public T cualquieraMenos(T elemento) {
        int pos = 0;
        NodoGenerico<T> aux = cabeza;
        while (aux != null) {

            if (elemento != aux.getDato()) return aux.getDato();
            pos++;
            aux = aux.getSiguiente();
        }
        return null;
    }
}
