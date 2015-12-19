package es.upm.dit.adsw.trenes.ejemplos.tunel;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Ejercicio 3
 * - se permiten N trenes dentro en la misma direccion.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class MonitorTunel_3
        extends Monitor {
    private int ocupado12 = 0;
    private int ocupado21 = 0;

    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        switch (tag) {
            case 1:
                while (ocupado21 > 0)
                    waiting();
                ocupado12++;
                break;
            case 2:
                while (ocupado12 > 0)
                    waiting();
                ocupado21++;
                break;
        }
    }

    public synchronized void salgo(int tag, Tren tren, Tramo tramo, Enlace salida) {
        switch (tag) {
            case 1:
                ocupado12--;
                break;
            case 2:
                ocupado21--;
                break;
        }
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
