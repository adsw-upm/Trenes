package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class Cruce
        extends Tramo {
    public Cruce(int cx, int cy) {
        super(cx, cy);
        setImagen("Cruce_nbg.png");
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        if (entrada == Enlace.N)
            return Enlace.S;
        if (entrada == Enlace.S)
            return Enlace.N;
        if (entrada == Enlace.E)
            return Enlace.W;
        if (entrada == Enlace.W)
            return Enlace.E;
        return null;
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return true;
    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double ds1 = 0.1 * velocidad;
        double x0 = xy[0];
        double y0 = xy[1];
        double x2 = x0;
        double y2 = y0;
        if (entrada == Enlace.N)
            y2 = y0 - ds1;
        else if (entrada == Enlace.S)
            y2 = y0 + ds1;
        else if (entrada == Enlace.E)
            x2 = x0 - ds1;
        else
            x2 = x0 + ds1;
        xy[0] = x2;
        xy[1] = y2;
        if (y2 > y0 && (1 - y2) < ds1)
            return Enlace.N;
        if (y2 < y0 && y2 < ds1)
            return Enlace.S;
        if (x2 > x0 && (1 - x2) < ds1)
            return Enlace.E;
        if (x2 < x0 && x2 < ds1)
            return Enlace.W;
        return null;
    }
}
