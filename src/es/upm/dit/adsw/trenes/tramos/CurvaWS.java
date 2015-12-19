package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class CurvaWS
        extends Tramo {
    public CurvaWS(int cx, int cy) {
        super(cx, cy);
        setImagen("CurvaWS_nbg.png");
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return enlace == Enlace.W || enlace == Enlace.S;
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        if (entrada == Enlace.W)
            return Enlace.S;
        else
            return Enlace.W;
    }

//    @Override
//    public double tTransito(Tren tren) {
//        return 0.5 / tren.getVelocidad();
//    }

//    @Override
//    public Enlace mueve(Enlace entrada, double[] xy, double velocidad, int tic) {
//        double ds1 = 0.1 * velocidad;
//        double ang = 2 * ds1 * tic;
//        if (entrada == Enlace.W) {
//            xy[0] = 0.5 * Math.sin(ang);
//            xy[1] = 0.5 * Math.cos(ang);
//            return salePorS(xy, ds1);
//        } else {
//            xy[0] = 0.5 * Math.cos(ang);
//            xy[1] = 0.5 * Math.sin(ang);
//            return salePorW(xy, ds1);
//        }
//    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        double ds1 = 0.2 * velocidad;
        double x0 = xy[0];
        double y0 = xy[1];
        double ang0 = Math.atan2(y0, x0);
        double ang = entrada == Enlace.S ? ang0 + ds1 : ang0 - ds1;
        double x2 = 0.5 * Math.cos(ang);
        double y2 = 0.5 * Math.sin(ang);
        xy[0] = x2;
        xy[1] = y2;
        if (x2 < x0 && x2 < ds1)
            return Enlace.W;
        if (y2 < y0 && y2 < ds1)
            return Enlace.S;
        return null;
    }
}
