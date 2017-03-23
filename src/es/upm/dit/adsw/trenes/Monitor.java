package es.upm.dit.adsw.trenes;

import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * @author Jose A. Manas
 * @version 21.3.2017
 */
public abstract class Monitor {

    /**
     * Metodo para el usuario. Se llama cuando un tren quiere entrar a un tramo monitorizado.
     * El usuario debe programar lo que haya que hacer.
     *
     * @param tag     pista para el monitor.
     * @param tren    tren que entra.
     * @param tramo   tramo al que va a entrar.
     * @param entrada enlace por el que entra.
     */
    public abstract void entro(int tag, Tren tren, Tramo tramo, Enlace entrada);

    /**
     * Metodo para el usuario. Se llama cuando un tren sale de un tramo monitorizado.
     * El usuario debe programar lo que haya que hacer.
     *
     * @param tag    pista para el monitor.
     * @param tren   tren que sale.
     * @param tramo  tramo del que va a salir.
     * @param salida enlace por el que sale.
     */
    public abstract void salgo(int tag, Tren tren, Tramo tramo, Enlace salida);
}
