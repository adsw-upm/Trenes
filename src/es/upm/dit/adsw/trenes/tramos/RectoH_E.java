package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class RectoH_E
        extends RectoH {
    public RectoH_E(int cx, int cy) {
        super(cx, cy);
        setImagen("RectoHE_nbg.png");
    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double x0 = xy[0];
        Enlace salida = super.mueve(entrada, xy, velocidad);
        double x2 = xy[0];
        if (x0 < 0.5 && x2 >= 0.5) {
            getTren().parar();
            return null;
        }
        if (x0 > 0.5 && x2 <= 0.5) {
            getTren().parar();
            return null;
        }
        return salida;
    }
}
