package es.upm.dit.adsw.trenes;

import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.util.concurrent.Semaphore;

/**
 * Tramo-Enlace-Semaforo
 * @author Jose A. Manas
 * @version 5.12.2015
 */
public class TES {
    public final Tramo tramo;
    public final Enlace enlace;
    public final Semaphore semaforo;

    public TES(Tramo tramo, Enlace enlace, Semaphore semaforo) {
        this.tramo = tramo;
        this.enlace = enlace;
        this.semaforo = semaforo;
    }
}
