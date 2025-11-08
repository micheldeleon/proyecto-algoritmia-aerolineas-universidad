package dominio;

public class NodoGenerico<T> {

    public T Dato;
    public NodoGenerico<T> Siguiente;

    public NodoGenerico(T Dato) {
        this.Dato = Dato;
        this.Siguiente = null;
    }

    public T getDato() {
        return Dato;
    }

    public void setDato(T Dato) {
        this.Dato = Dato;
    }

    public NodoGenerico<T> getSiguiente() {
        return Siguiente;
    }

    public void setSiguiente(NodoGenerico<T> Siguiente) {
        this.Siguiente = Siguiente;
    }


}