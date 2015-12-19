package es.upm.dit.adsw.trenes;

import es.upm.dit.adsw.trenes.tramos.Tramo;

/**
 * Tramo-Enlace-Monitor
 * @author Jose A. Manas
 * @version 5.12.2015
 */
class TEM {
    final Tramo tramo;
    final Enlace enlace;
    final Monitor monitor;
    final int tag;

    public TEM(Tramo tramo, Enlace enlace, Monitor monitor, int tag) {
        this.tramo = tramo;
        this.enlace = enlace;
        this.monitor = monitor;
        this.tag = tag;
    }
}
