package es.upm.dit.adsw.trenes.ejemplos;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo de monitor de un tramo compartido.
 * Deja pasar varios trenes en la misma direccion.
 *
 * @author Jose A. Manas
 * @version 9/3/2012
 */
public class MonitorTunel
        extends Monitor {
    private List<Tren> circulando12 = new ArrayList<Tren>();
    private List<Tren> circulando21 = new ArrayList<Tren>();

    public synchronized void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
        switch (tag) {
            case 1:
                while (circulando21.size() > 0)
                    waiting();
                circulando12.add(tren);
                break;
            case 2:
                while (circulando12.size() > 0)
                    waiting();
                circulando21.add(tren);
                break;
        }
    }

    public synchronized void salgo(int tag, Tren tren, Tramo tramo, Enlace salida) {
        switch (tag) {
            case 2:
                circulando21.remove(tren);
                notifyAll();
                break;
            case 1:
                circulando12.remove(tren);
                notifyAll();
                break;
        }
    }

    private void waiting() {
        try {
            wait();
        } catch (InterruptedException ignored) {
        }
    }
}
