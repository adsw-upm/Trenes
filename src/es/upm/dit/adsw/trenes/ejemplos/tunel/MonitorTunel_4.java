package es.upm.dit.adsw.trenes.ejemplos.tunel;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Ejercicio 4:
 * - N trenes en la misma direccion
 * - reparto equitativo de entradas.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class MonitorTunel_4
        extends Monitor {
    private int esperando1 = 0;
    private int esperando2 = 0;
    private int ocupado12 = 0;
    private int ocupado21 = 0;
    private int ultimaEntrada = 1;

    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        switch (tag) {
            case 1:
                esperando1++;
                while (!puedoPasar(ocupado21, 1, esperando2))
                    waiting();
                esperando1--;
                ocupado12++;
                ultimaEntrada = 1;
                break;
            case 2:
                esperando2++;
                while (!puedoPasar(ocupado12, 2, esperando1))
                    waiting();
                esperando2--;
                ocupado21++;
                ultimaEntrada = 2;
                break;
        }
    }

    private boolean puedoPasar(int ocupado, int tramo, int esperando) {
        if (ocupado > 0)
            return false;
        if (ultimaEntrada == tramo && esperando > 0)
            return false;
        return true;
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
