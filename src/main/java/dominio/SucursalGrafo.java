package dominio;

import java.util.Arrays;

public class SucursalGrafo extends Exception {

    private Conexion[][] conexionesMatriz; //aristas
    private NodoGenerico<Sucursal>[] sucursales; //vertices
    private int cantidad;
    private int capacidadMaxima;

    // Constructor
    public SucursalGrafo(int capacidadMax) {
        this.capacidadMaxima = capacidadMax;
        this.conexionesMatriz = new Conexion[capacidadMax][capacidadMax];
        this.sucursales = new NodoGenerico[capacidadMax];
        this.cantidad = 0;
        inicializarConexiones();
    }

    public void inicializarConexiones() {
        for (int i = 0; i < capacidadMaxima; i++) {
            for (int j = 0; j < capacidadMaxima; j++) {
                conexionesMatriz[i][j] = new Conexion(); // Inicializar todas las conexiones como false
            }
        }
    }

    public boolean estaLleno() {
        return cantidad >= capacidadMaxima;
    }

    public boolean existe(String codigo) {
        for (int i = 0; i < sucursales.length; i++) {
            if (sucursales[i] != null && sucursales[i].getDato() != null) {
                String codigoActual = sucursales[i].getDato().getCodigo();
                if (codigoActual.equals(codigo)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void agregar(NodoGenerico<Sucursal> sucursal) {
        if (cantidad < capacidadMaxima) {
            sucursales[cantidad] = sucursal;
            cantidad++;
        } else {
            throw new ArrayIndexOutOfBoundsException("No se pudo agregar el elemento: el array está lleno.");
        }
    }

    // Agrega una arista entre dos nodos
    public void agregarConexion(String sucursal1, String sucursal2, int latencia) {
        int indiceSucursal1 = obtenerIndice(sucursal1);
        int indiceSucursal2 = obtenerIndice(sucursal2);
        if (indiceSucursal1 != -1 && indiceSucursal2 != -1 && indiceSucursal1 != indiceSucursal2) {
            conexionesMatriz[indiceSucursal1][indiceSucursal2] = new Conexion(sucursal1, sucursal2, latencia, true);
            conexionesMatriz[indiceSucursal2][indiceSucursal1] = new Conexion(sucursal2, sucursal1, latencia, true);
        }
    }

    public int obtenerIndice(String sucursal) {
        for (int i = 0; i < sucursales.length; i++) {
            if (sucursales[i] != null && sucursales[i].getDato() != null) {
                String codigo = sucursales[i].getDato().getCodigo();
                if (codigo.equals(sucursal)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean existeConexion(String sucursal1, String sucursal2) {
        int indiceSucursal1 = obtenerIndice(sucursal1);
        int indiceSucursal2 = obtenerIndice(sucursal2);
        if (indiceSucursal1 == -1 || indiceSucursal2 == -1) {
            return false;
        }
        Conexion conexion = conexionesMatriz[indiceSucursal1][indiceSucursal2];
        return conexion != null && conexion.isExiste();
    }

    public Conexion obtenerConexion(String sucursal, String sucursal2) {
        int indiceSucursal1 = obtenerIndice(sucursal);
        int indiceSucursal2 = obtenerIndice(sucursal2);
        if (indiceSucursal1 != -1 || indiceSucursal2 != -1) {
            return conexionesMatriz[indiceSucursal1][indiceSucursal2];
        }
        return null;
    }

    public void updateLatencia(String sucursal1, String sucursal2, int latencia) {
        int indiceSucursal1 = obtenerIndice(sucursal1);
        int indiceSucursal2 = obtenerIndice(sucursal2);
        if (indiceSucursal1 != -1 || indiceSucursal2 != -1) {
            conexionesMatriz[indiceSucursal1][indiceSucursal2].setLatencia(latencia);
            conexionesMatriz[indiceSucursal2][indiceSucursal1].setLatencia(latencia);
        }
    }


    private NodoGenerico<Sucursal>[] sucursalesParaTorneo(String codigo, int latencia) {
        NodoGenerico<Sucursal>[] copia = new NodoGenerico[capacidadMaxima];
        int cont = 0;
        for (int i = 0; i < sucursales.length; i++) {
            Sucursal actual = sucursales[i].getDato();
            Conexion conexion = obtenerConexion(codigo, actual.getCodigo());
            if (conexion != null) {
                if (conexion.getLatencia() <= latencia) {
                    copia[cont] = sucursales[i];
                    cont++;
                }
            }
        }
        Arrays.sort(copia);
        return copia;
    }


    public String esArticulacion(String codigo) {
        // Obtener el componente conexo completo desde el nodo de origen
        ListaEnlazada<Sucursal> componenteConexa = dfsIterativo(codigo);
        if (componenteConexa.esVacia() || componenteConexa.cantidad() == 1) return "NO";
        // Obtener el componente conexo sin el nodo candidato
        String sucursalPrueba = componenteConexa.cualquieraMenos(new Sucursal(codigo)).getCodigo();
        ListaEnlazada<Sucursal> componenteSinNodo = dfsIterativoIgnorandoNodo(sucursalPrueba, codigo);
        // Comparar los tamaños de los componentes
        String diferencia = (componenteConexa.cantidad() - componenteSinNodo.cantidad()) >= 2 ? "SI" : "NO";
        // Si la diferencia es mayor o igual a 2, el nodo es una articulación
        return diferencia;
    }

    public ListaEnlazada<Sucursal> dfsIterativoIgnorandoNodo(String origen, String nodoIgnorado) {
        ListaEnlazada<Sucursal> respuesta = new ListaEnlazada<Sucursal>();
        ListaEnlazada<Integer> frontera = new ListaEnlazada<>();
        int vOrigen = obtenerIndice(origen);
        int vIgnorado = obtenerIndice(nodoIgnorado); // Obtener el índice del nodo a ignorar
        boolean[] visitados = new boolean[capacidadMaxima];

        // Agrega el nodo inicial a la frontera solo si no es el nodo ignorado
        if (vOrigen != vIgnorado) {
            frontera.agregarInicio(vOrigen);
        }

        while (!frontera.esVacia()) {
            int vExplorar = frontera.getCabeza().getDato(); // Obtiene el nodo a explorar
            frontera.eliminarAlInicio(); // Elimina el nodo actual de la frontera
            // Ignora el nodo si es el que queremos omitir o si ya fue visitado
            if (vExplorar == vIgnorado || visitados[vExplorar]) {
                continue;
            }
            // Marca el nodo como visitado
            visitados[vExplorar] = true;
            // Agrega el nodo a la lista de respuesta
            respuesta.agregarInicio(sucursales[vExplorar].getDato());

            // Explora los vecinos del nodo actual
            for (int vDestino = 0; vDestino < capacidadMaxima; vDestino++) {
                if (conexionesMatriz[vExplorar][vDestino].isExiste() && !visitados[vDestino] && vDestino != vIgnorado) {
                    frontera.agregarInicio(vDestino); // Agrega el vecino no visitado a la frontera
                }
            }
        }
        return respuesta;
    }


    public ListaEnlazada<Sucursal> dfsIterativo(String origen) {
        ListaEnlazada<Sucursal> respuesta = new ListaEnlazada<Sucursal>();
        ListaEnlazada<Integer> frontera = new ListaEnlazada<>();
        int vOrigen = obtenerIndice(origen);
        boolean[] visitados = new boolean[capacidadMaxima];
        frontera.agregarInicio(vOrigen); // Agrega el nodo inicial a la frontera
        while (!frontera.esVacia()) {
            int vExplorar = frontera.getCabeza().getDato(); // Obtiene el nodo a explorar
            frontera.eliminarAlInicio(); // Elimina el nodo actual de la frontera
            if (!visitados[vExplorar]) {
                // Marca el nodo como visitado
                visitados[vExplorar] = true;
                // Agrega el nodo a la lista de respuesta
                respuesta.agregarInicio(sucursales[vExplorar].getDato());
                // Explora los vecinos del nodo actual
                for (int vDestino = 0; vDestino < capacidadMaxima; vDestino++) {
                    if (conexionesMatriz[vExplorar][vDestino].isExiste() && !visitados[vDestino]) {
                        frontera.agregarInicio(vDestino); // Agrega el vecino no visitado a la frontera
                    }
                }
            }
        }
        return respuesta;
    }

    public class ResultadoImprimir {
        String resultado;
        int costoMayorEncontrado;

        public ResultadoImprimir(String resultado, int costoMayorEncontrado) {
            this.resultado = resultado;
            this.costoMayorEncontrado = costoMayorEncontrado;
        }

        public int getCostoMayorEncontrado() {
            return costoMayorEncontrado;
        }

        public String getResultado() {
            return resultado;
        }
    }

    public ResultadoImprimir imprimirSucursalesParaTorneo(String origen, int latenciaLimite) {
        ArbolABBGenerico<Sucursal> listaSucursale = sucursalesTorneoPrueba(origen, latenciaLimite);
        // Usamos el método imprimirOrdenadoAsc del árbol para obtener la cadena con las sucursales ordenadas
        StringBuilder resultado = listaSucursale.imprimirOrdenadoAscDis();
        ResultadoImprimir respuesta = new ResultadoImprimir(String.valueOf(resultado), costosG);
        costosG = 0;
        return respuesta;
    }


    public ArbolABBGenerico sucursalesTorneoPrueba(String sOrigen, int costoMaximo) {
        int origen = obtenerIndice(sOrigen);
        int[] costos = new int[capacidadMaxima];
        int[] padres = new int[capacidadMaxima];
        boolean[] visitados = new boolean[capacidadMaxima];
        inicializadorArrays(costos, padres);
        costos[origen] = 0;
        padres[origen] = origen;
        while (!todoVisitado(visitados, padres)) {
            int sExplorar = minimoVerticeNoVisitado(costos, padres, visitados);
            // Si el costo mínimo del vértice actual ya supera el costo máximo, se detiene la exploración
            if (costos[sExplorar] > costoMaximo) {
                break;
            }
            for (int i = 0; i < capacidadMaxima; i++) {
                Conexion conexion = conexionesMatriz[sExplorar][i];
                if (conexion.isExiste()) {
                    int latencia = conexion.getLatencia();
                    int distancia = costos[sExplorar] + latencia;
                    if (distancia <= costos[i]) {
                        costos[i] = distancia;
                        padres[i] = sExplorar;
                    }
                }
            }
            visitados[sExplorar] = true;
        }
        return obtenerArbolSucursales(origen, padres, costoMaximo, costos);
    }

    int costosG = 0;

    private ArbolABBGenerico<Sucursal> obtenerArbolSucursales(int sOrigen, int[] padres, int costoMaximo, int[] costos) {
        ArbolABBGenerico<Sucursal> respuesta = new ArbolABBGenerico<>();
        for (int i = 0; i < costos.length; i++) {
            if (padres[i] != -1 && costos[i] <= costoMaximo && i != sOrigen) {
                respuesta.agregar(sucursales[i].getDato());
                if (costos[i] > costosG) {
                    costosG = costos[i];
                }
            }
        }
        // Agregar el origen al árbol
        respuesta.agregar(sucursales[sOrigen].getDato());
        return respuesta;
    }

    private void inicializadorArrays(int[] costos, int[] padres) {
        for (int i = 0; i < capacidadMaxima; i++) {
            costos[i] = Integer.MAX_VALUE;
            padres[i] = -1;
        }
    }

    private int minimoVerticeNoVisitado(int[] costos, int[] padres, boolean[] visitados) {
        int minIndice = -1;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < capacidadMaxima; i++) {
            if (!visitados[i] && costos[i] < min && padres[i] != -1) {
                min = costos[i];
                minIndice = i;
            }
        }
        return minIndice;
    }

    private boolean todoVisitado(boolean[] visitados, int[] padres) {
        for (int i = 0; i < capacidadMaxima; i++) {
            if (padres[i] != -1) {
                if (!visitados[i]) {
                    return false;
                }
            }
        }
        return true;
    }


}
