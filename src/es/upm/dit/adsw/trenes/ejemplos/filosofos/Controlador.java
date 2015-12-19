package es.upm.dit.adsw.trenes.ejemplos.filosofos;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * El problema de los filosofos.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class Controlador
        extends Monitor {
    private final int N;
    private boolean[] recursos;

    public Controlador(int N) {
        this.N = N;
        recursos = new boolean[N];
    }

    @Override
    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        int sig = (tag + 1) % N;
        while (recursos[tag] || recursos[sig])
            waiting();
        recursos[tag] = true;
        recursos[sig] = true;
    }

    @Override
    public synchronized void salgo(int tag, Tren tren, Tramo tramo, Enlace salida) {
        int sig = (tag + 1) % N;
        recursos[tag] = false;
        recursos[sig] = false;
        notifyAll();
    }

    private void waiting() {
        try {
            wait();
        } catch (InterruptedException ignored) {
        }
    }
}
