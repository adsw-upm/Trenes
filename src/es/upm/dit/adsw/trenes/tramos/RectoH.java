package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class RectoH
        extends Tramo {
    public RectoH(int cx, int cy) {
        super(cx, cy);
        setImagen("RectoH_nbg.png");
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return enlace == Enlace.E || enlace == Enlace.W;
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        return entrada.opuesto();
    }

//    @Override
//    public Enlace mueve(Enlace entrada, double[] xy, double velocidad, int tic) {
//        double ds1 = 0.1 * velocidad;
//        double ds = ds1 * tic;
//        xy[1] = 0.5;
//        if (entrada == Enlace.E) {
//            xy[0] = 1 - ds;
//            return salePorW(xy, ds1);
//        } else {
//            xy[0] = ds;
//            return salePorE(xy, ds1);
//        }
//    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double ds1 = 0.1 * velocidad;
        double x0 = xy[0];
        double x2 = entrada == Enlace.W ? x0 + ds1 : x0 - ds1;
        xy[0] = x2;
        ds1 = Math.abs(ds1);
        if (x2 < x0 && x2 < ds1)
            return Enlace.W;
        if (x2 > x0 && (1 - x2) < ds1)
            return Enlace.E;
        return null;
    }
}
