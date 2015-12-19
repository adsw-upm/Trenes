package es.upm.dit.adsw.trenes.ejemplos.tunel;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Ejercicio 1.1
 * - 1 tren dentro.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class MonitorTunel_1
        extends Monitor {
    private boolean ocupado = false;

    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        while (ocupado)
            waiting();
        ocupado = true;
    }

    public synchronized void salgo(int tag, Tren tren, Tramo tramo, Enlace salida) {
        ocupado = false;
        notifyAll();
    }

    /**
     * Metodo auxiliar.
     * Hace que la espera no sea interrumplible.
     * Sintacticamente queda más simple, aunque es menos general.
     */
    private void waiting() {
        try {
            wait();
        } catch (InterruptedException ignored) {
        }
    }
}
