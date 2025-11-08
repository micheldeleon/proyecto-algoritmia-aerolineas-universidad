package sistema;

import dominio.*;
import interfaz.Categoria;
import interfaz.Retorno;
import interfaz.Sistema;

public class ImplementacionSistema implements Sistema {


    private ArbolABBGenerico<Jugador> jugadores;
    private ArbolABBGenerico<Jugador> jugadoresPrincipiante;
    private ArbolABBGenerico<Jugador> jugadoresEstandar;
    private ArbolABBGenerico<Jugador> jugadoresProfesional;
    private ArbolABBGenerico<Equipo> equipos;
    private SucursalGrafo sucursales;


    @Override
    public Retorno inicializarSistema(int maxSucursales) {
        if (maxSucursales <= 3) {
            return Retorno.error1("El número máximo de sucursales debe ser mayor que 3.");
        }

        jugadores = new ArbolABBGenerico<Jugador>();
        jugadoresPrincipiante = new ArbolABBGenerico<Jugador>();
        jugadoresEstandar = new ArbolABBGenerico<Jugador>();
        jugadoresProfesional = new ArbolABBGenerico<Jugador>();
        equipos = new ArbolABBGenerico<Equipo>();
        sucursales = new SucursalGrafo(maxSucursales);

        return Retorno.ok();
    }

    private void AgregarJuACategoria(Jugador jugador) {
        if (jugador.getCategoria() == Categoria.PRINCIPIANTE) {
            jugadoresPrincipiante.agregar(jugador);
        }
        if (jugador.getCategoria() == Categoria.ESTANDARD) {
            jugadoresEstandar.agregar(jugador);
        }
        if (jugador.getCategoria() == Categoria.PROFESIONAL) {
            jugadoresProfesional.agregar(jugador);
        }
    }

    @Override
    public Retorno registrarJugador(String alias, String nombre, String apellido, Categoria categoria) {
        if (alias == null || alias.isEmpty() || nombre == null || nombre.isEmpty() || apellido == null || apellido.isEmpty() || categoria == null) {
            return Retorno.error1("ERROR 1");
        }
        Jugador nuevo = new Jugador(alias, nombre, apellido, categoria);
        if (jugadores.existe(nuevo)) {
            return Retorno.error2("ERROR 2");
        } else {
            AgregarJuACategoria(nuevo);
            jugadores.agregar(nuevo);
        }

        return Retorno.ok();
    }


    @Override
    public Retorno buscarJugador(String alias) {
        if (alias == null || alias.isEmpty()) {
            return Retorno.error1("ERROR 1");
        }
        Jugador JuBuscado = new Jugador(alias);
        Jugador jugador = jugadores.buscarElemento(JuBuscado);
        if (jugador == null) {
            return Retorno.error2("ERROR 2");
        }
        int cantElementos = jugadores.BuscarElementoContador(jugador);
        return new Retorno(Retorno.Resultado.OK, cantElementos, jugador.toString());
    }

    @Override
    public Retorno listarJugadoresAscendente() {
        StringBuilder JuOrdenadosAsc = jugadores.imprimirOrdenadoAsc();
        return Retorno.ok(String.valueOf(JuOrdenadosAsc));
    }

    @Override
    public Retorno listarJugadoresPorCategoria(Categoria unaCategoria) {
        if (unaCategoria == Categoria.PRINCIPIANTE) {
            return Retorno.ok(String.valueOf(jugadoresPrincipiante.imprimirOrdenadoAsc()));
        }
        if (unaCategoria == Categoria.ESTANDARD) {
            return Retorno.ok(String.valueOf(jugadoresEstandar.imprimirOrdenadoAsc()));
        }
        if (unaCategoria == Categoria.PROFESIONAL) {
            return Retorno.ok(String.valueOf(jugadoresProfesional.imprimirOrdenadoAsc()));
        }

        return Retorno.ok();
    }

    @Override
    public Retorno registrarEquipo(String nombre, String manager) {
        if (nombre == null || nombre.isEmpty() || manager == null || manager.isEmpty()) {
            return Retorno.error1("ERROR 1");
        }
        Equipo equipoBuscado = new Equipo(nombre, manager);

        if (equipos.existe(equipoBuscado)) {
            return Retorno.error2("ERROR 2");
        }
        equipos.agregar(equipoBuscado);

        return Retorno.ok();
    }

    @Override
    public Retorno agregarJugadorAEquipo(String nombreEquipo, String aliasJugador) {
        if (nombreEquipo == null || nombreEquipo.isEmpty() || aliasJugador == null || aliasJugador.isEmpty()) {
            return Retorno.error1("ERROR 1");
        }
        Equipo equipoBuscado = equipos.buscarElemento(new Equipo(nombreEquipo));
        if (equipoBuscado == null) {
            return Retorno.error2("ERROR 2");
        }
        Jugador jugadorBuscado = jugadores.buscarElemento(new Jugador(aliasJugador));
        if (jugadorBuscado == null) {
            return Retorno.error3("ERROR 3");
        }
        if (equipoBuscado.cantJugadores() >= 5) {
            return Retorno.error4("ERROR 4");
        }
        if (jugadorBuscado.getCategoria() != Categoria.PROFESIONAL) {
            return Retorno.error5("ERROR 5");
        }
        if (jugadorBuscado.tieneEquipo()) {
            return Retorno.error6("ERROR 6");
        }
        equipoBuscado.getJugadoresDelEquipo().agregar(jugadorBuscado);
        jugadorBuscado.setTieneEquipo(true);

        return Retorno.ok();
    }

    @Override
    public Retorno listarJugadoresDeEquipo(String nombreEquipo) {
        if (nombreEquipo == null || nombreEquipo.isEmpty()) {
            return Retorno.error1("ERROR 1");
        }
        Equipo equipo = equipos.buscarElemento(new Equipo(nombreEquipo));
        if (equipo == null) {
            return Retorno.error2("ERROR 2");
        }
        return Retorno.ok(String.valueOf(equipo.getJugadoresDelEquipo().imprimirOrdenadoAsc()));
    }

    @Override
    public Retorno listarEquiposDescendente() {
        StringBuilder equiposOrdenadoDes = equipos.imprimirOrdenadoDes();
        return Retorno.ok(String.valueOf(equiposOrdenadoDes));
    }

    @Override
    public Retorno registrarSucursal(String codigo, String nombre) {
        if (sucursales.estaLleno()) {
            return Retorno.error1("ERROR 1");
        } else if (codigo == null || codigo.isEmpty() || nombre == null || nombre.isEmpty()) {
            return Retorno.error2("ERROR 2");
        } else if (sucursales.existe(codigo)) {
            return Retorno.error3("ERROR 3");
        }
        sucursales.agregar(new NodoGenerico<>(new Sucursal(codigo, nombre)));

        return Retorno.ok();
    }

    @Override
    public Retorno registrarConexion(String codigoSucursal1, String codigoSucursal2, int latencia) {
        if (latencia < 0) {
            return Retorno.error1("ERROR 1");
        } else if (codigoSucursal1 == null || codigoSucursal1.isEmpty()
                || codigoSucursal2 == null || codigoSucursal2.isEmpty()) {
            return Retorno.error2("ERROR 2");
        } else if (!sucursales.existe(codigoSucursal1) || !sucursales.existe(codigoSucursal2)) {
            return Retorno.error3("ERROR 3");
        } else if (sucursales.existeConexion(codigoSucursal1, codigoSucursal2)) {
            return Retorno.error4("ERROR 4");
        }
        sucursales.agregarConexion(codigoSucursal1, codigoSucursal2, latencia);

        return Retorno.ok();
    }

    @Override
    public Retorno actualizarConexion(String codigoSucursal1, String codigoSucursal2, int latencia) {
        if (latencia < 0) {
            return Retorno.error1("ERROR 1");
        } else if (codigoSucursal1 == null || codigoSucursal1.isEmpty() || codigoSucursal2 == null || codigoSucursal2.isEmpty()) {
            return Retorno.error2("ERROR 2");
        } else if (!sucursales.existe(codigoSucursal1) || !sucursales.existe(codigoSucursal2)) {
            return Retorno.error3("ERROR 3");
        } else if (!sucursales.existeConexion(codigoSucursal1, codigoSucursal2)) {
            return Retorno.error4("ERROR 4");
        }
        sucursales.updateLatencia(codigoSucursal1, codigoSucursal2, latencia);

        return Retorno.ok();
    }

    @Override
    public Retorno analizarSucursal(String codigoSucursal) {
        if (codigoSucursal == null || codigoSucursal.isEmpty()) {
            return Retorno.error1("ERROR 1");
        }
        if (!sucursales.existe(codigoSucursal)) {
            return Retorno.error2("ERROR 2");
        }

        return Retorno.ok(sucursales.esArticulacion(codigoSucursal));
    }

    @Override
    public Retorno sucursalesParaTorneo(String codigoSucursalAnfitriona, int latenciaLimite) {
        if (codigoSucursalAnfitriona == null || codigoSucursalAnfitriona.isEmpty()) {
            return Retorno.error1("ERROR 1");
        } else if (!sucursales.existe(codigoSucursalAnfitriona)) {
            return Retorno.error2("ERROR 2");
        } else if (latenciaLimite <= 0) {
            return Retorno.error3("ERROR 3");
        }
        SucursalGrafo.ResultadoImprimir resultado = sucursales.imprimirSucursalesParaTorneo(codigoSucursalAnfitriona, latenciaLimite);
        
        return Retorno.ok(resultado.getCostoMayorEncontrado(), resultado.getResultado());
    }
}
