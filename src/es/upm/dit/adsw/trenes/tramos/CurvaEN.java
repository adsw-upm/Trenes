package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class CurvaEN
        extends Tramo {
    public CurvaEN(int cx, int cy) {
        super(cx, cy);
        setImagen("CurvaEN_nbg.png");
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return enlace == Enlace.E || enlace == Enlace.N;
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        if (entrada == Enlace.N)
            return Enlace.E;
        else
            return Enlace.N;
    }

//    @Override
//    public Enlace mueve(Enlace entrada, double[] xy, double velocidad, int tic) {
//        double ds1 = 0.1 * velocidad;
//        double ang = 2 * ds1 * tic;
//        if (entrada == Enlace.E) {
//            xy[0] = 1 - 0.5 * Math.sin(ang);
//            xy[1] = 1 - 0.5 * Math.cos(ang);
//            return salePorN(xy, ds1);
//        } else {
//            xy[0] = 1 - 0.5 * Math.cos(ang);
//            xy[1] = 1 - 0.5 * Math.sin(ang);
//            return salePorE(xy, ds1);
//        }
//    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double ds1 = 0.2 * velocidad;
        double x0 = xy[0];
        double y0 = xy[1];
        double ang0 = Math.atan2(1 - y0, 1 - x0);
        double ang = entrada == Enlace.N ? ang0 + ds1 : ang0 - ds1;
        double x2 = 1 - 0.5 * Math.cos(ang);
        double y2 = 1 - 0.5 * Math.sin(ang);
        xy[0] = x2;
        xy[1] = y2;
        if (x2 > x0 && (1 - x2) < ds1)
            return Enlace.E;
        if (y2 > y0 && (1 - y2) < ds1)
            return Enlace.N;
        return null;
    }
}
