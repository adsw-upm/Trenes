package es.upm.dit.adsw.trenes.ejemplos.tunel;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Base
 * - no se controla nada.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class MonitorTunel
        extends Monitor {

    // la thread se detiene hasta que pueda entrar
    public void entro(int tag, Tren tren, Tramo tramo, Enlace entrada) {
    }

    // la thread sale
    public void salgo(int tag, Tren tren, Tramo tramo, Enlace salida) {
    }

}
