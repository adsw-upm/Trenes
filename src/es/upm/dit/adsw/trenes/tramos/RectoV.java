package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class RectoV
        extends Tramo {
    public RectoV(int cx, int cy) {
        super(cx, cy);
        setImagen("RectoV_nbg.png");
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return enlace == Enlace.N || enlace == Enlace.S;
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        return entrada.opuesto();
    }

//    @Override
//    public Enlace mueve(Enlace entrada, double[] xy, double velocidad, int tic) {
//        double ds1 = 0.1 * velocidad;
//        double ds = ds1 * tic;
//        xy[0] = 0.5;
//        if (entrada == Enlace.N) {
//            xy[1] = 1 - ds;
//            return salePorS(xy, ds1);
//        } else {
//            xy[1] = ds;
//            return salePorN(xy, ds1);
//        }
//    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double ds1 = 0.1 * velocidad;
        double y0 = xy[1];
        double y2 = entrada == Enlace.S ? y0 + ds1 : y0 - ds1;
        xy[1] = y2;
        if (y2 < y0 && y2 < ds1)
            return Enlace.S;
        if (y2 > y0 && (1 - y2) < ds1)
            return Enlace.N;
        return null;
    }
}
