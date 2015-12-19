package es.upm.dit.adsw.trenes.ejemplos.tunel;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Ejercicio 2
 * - 1 tren dentro
 * - reparto equitativo de entradas.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class MonitorTunel_2
        extends Monitor {
    private int esperando1 = 0;
    private int esperando2 = 0;
    private boolean ocupado = false;
    private int ultimaEntrada = 1;

    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        switch (tag) {
            case 1:
                esperando1++;
                while (ocupado || ultimaEntrada == 1 && esperando2 > 0)
                    waiting();
                esperando1--;
                ocupado = true;
                ultimaEntrada = 1;
                break;
            case 2:
                esperando2++;
                while (ocupado || ultimaEntrada == 2 && esperando1 > 0)
                    waiting();
                esperando2--;
                ocupado = true;
                ultimaEntrada = 2;
                break;
        }
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
