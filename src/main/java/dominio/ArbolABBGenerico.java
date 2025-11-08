package dominio;


public class ArbolABBGenerico<T extends Comparable<T>> {

    private NodoAB raiz;

    public class NodoAB {
        private NodoAB izq;
        private NodoAB der;
        private T dato;

        public NodoAB(NodoAB izq, NodoAB der, T dato) {
            this.izq = izq;
            this.der = der;
            this.dato = dato;
        }

        public NodoAB(T dato) {
            this.dato = dato;
        }
    }

    private boolean esMayor(NodoAB nodo, T datoABuscar) {
        return nodo.dato.compareTo(datoABuscar) > 0;
    }

    private boolean esMenor(NodoAB nodo, T datoABuscar) {
        return nodo.dato.compareTo(datoABuscar) < 0;
    }

    private boolean esIgual(NodoAB nodo, T datoABuscar) {
        return nodo.dato.compareTo(datoABuscar) == 0;
    }

    public void agregar(T dato) {
        this.raiz = agregar(raiz, dato);
    }

    private NodoAB agregar(NodoAB nodo, T datoAAgregar) {
        if (nodo == null) {
            return new NodoAB(datoAAgregar);
        }
        if (esIgual(nodo, datoAAgregar)) {
            return nodo;
        } else if (esMenor(nodo, datoAAgregar)) {
            nodo.der = agregar(nodo.der, datoAAgregar);
        } else {
            nodo.izq = agregar(nodo.izq, datoAAgregar);
        }
        return nodo;
    }

    public boolean existe(T dato) {
        return existe(raiz, dato);
    }

    private boolean existe(NodoAB nodo, T datoABuscar) {
        if (nodo == null) return false;
        if (esIgual(nodo, datoABuscar)) return true;
        if (esMayor(nodo, datoABuscar)) {
            return existe(nodo.izq, datoABuscar);
        } else return existe(nodo.der, datoABuscar);
    }


    public StringBuilder imprimirOrdenadoDes() {
        StringBuilder resultado = new StringBuilder();
        imprimirOrdenadoDes(raiz, resultado);
        if (resultado.length() > 0) {
            resultado.deleteCharAt(resultado.length() - 1);
        }
        return resultado;
    }

    private void imprimirOrdenadoDes(NodoAB nodo, StringBuilder resultado) {
        if (nodo != null) {
            imprimirOrdenadoDes(nodo.der, resultado);
            resultado.append(nodo.dato.toString()).append("|");//ACORDATE CUANDO JUSTIFIQUES QUE EL append tiene O(1)
            imprimirOrdenadoDes(nodo.izq, resultado);
        }
    }

    public StringBuilder imprimirOrdenadoAscDis() {
        StringBuilder resultado = new StringBuilder();
        imprimirOrdenadoAscDis(raiz, resultado);
        if (resultado.length() > 0) {
            resultado.deleteCharAt(resultado.length() - 1);
        }
        return resultado;
    }

    private void imprimirOrdenadoAscDis(NodoAB nodo, StringBuilder resultado) {
        if (nodo != null) {
            imprimirOrdenadoAscDis(nodo.izq, resultado);
            Sucursal sucursal = (Sucursal) nodo.dato;
            String sucursals = (sucursal.getNombre());
            resultado.append(sucursal.getCodigo())
                    .append(";")
                    .append(sucursal.getNombre())
                    .append("|");

            imprimirOrdenadoAscDis(nodo.der, resultado);
        }
    }

    public StringBuilder imprimirOrdenadoAsc() {
        StringBuilder resultado = new StringBuilder();
        imprimirOrdenadoAsc(raiz, resultado);
        if (resultado.length() > 0) {
            resultado.deleteCharAt(resultado.length() - 1);
        }
        return resultado;
    }

    private void imprimirOrdenadoAsc(NodoAB nodo, StringBuilder resultado) {
        if (nodo != null) {

            imprimirOrdenadoAsc(nodo.izq, resultado);
            resultado.append(nodo.dato.toString()).append("|");
            imprimirOrdenadoAsc(nodo.der, resultado);
        }
    }

    public T buscarElemento(T dato) {
        return buscarElemento(dato, raiz);
    }

    private T buscarElemento(T dato, NodoAB nodo) {
        if (nodo == null) return null;
        else if (esIgual(nodo, dato)) return nodo.dato;
        else if (esMenor(nodo, dato)) return buscarElemento(dato, nodo.der);
        else return buscarElemento(dato, nodo.izq);
    }

    public int cantidad() {
        return cantidad(raiz);
    }

    private int cantidad(NodoAB nodo) {
        if (nodo == null) return 0;
        return cantidad(nodo.izq) + 1 + cantidad(nodo.der);
    }

    public int BuscarElementoContador(T dato) {
        return buscarElementoContadorRec(dato, raiz, 1);
    }

    private int buscarElementoContadorRec(T dato, NodoAB nodo, int contador) {
        if (nodo == null) return 0;
        else if (esIgual(nodo, dato)) return contador;
        else if (esMenor(nodo, dato)) return buscarElementoContadorRec(dato, nodo.der, contador + 1);
        else return buscarElementoContadorRec(dato, nodo.izq, contador + 1);
    }

    public NodoAB elementoMaximo() {
        return elementoMaximoRec(raiz);
    }

    private NodoAB elementoMaximoRec(NodoAB nodo) {
        if (nodo.der == null) {
            return nodo;
        } else {
            return elementoMaximoRec(nodo.der);
        }
    }

}
