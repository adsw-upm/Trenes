package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Tren;

import java.awt.*;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public class Desvio
        extends Tramo {
    private final Tramo curvo;
    private final Tramo recto;
    private final Image imagenRecto;
    private final Image imagenDesvio;

    private Tramo actual;

    public Desvio(Tramo curvo, Tramo recto) {
        super(curvo.getCx(), curvo.getCy());
        this.curvo = curvo;
        this.recto = recto;
        Image ic = curvo.getImagen();
        Image ir = recto.getImagen();
        imagenRecto = compose(ic, ir);
        imagenDesvio = compose(ir, ic);
        setRecto(true);
    }

    @Override
    public void setRecto(boolean s) {
        super.setRecto(s);
        if (isRecto()) {
            setImagen(imagenRecto);
        } else {
            setImagen(imagenDesvio);
        }
    }

    @Override
    public void entra(Tren tren, Enlace entrada)
            throws InterruptedException {
        super.entra(tren, entrada);
        if (curvo.hayEntrada(entrada) && recto.hayEntrada(entrada)) {
            if (isRecto())
                actual = recto;
            else
                actual = curvo;
        } else if (curvo.hayEntrada(entrada)) {
            actual = curvo;
        } else {
            actual = recto;
        }
    }

    @Override
    public boolean hayEntrada(Enlace enlace) {
        return curvo.hayEntrada(enlace) || recto.hayEntrada(enlace);
    }

    @Override
    public Enlace salePor(Enlace entrada) {
        return actual.salePor(entrada);
    }

    @Override
    public Enlace mueve(Enlace entrada, double[] xy, double velocidad) {
        if (actual == null)
            return null;
        return actual.mueve(entrada, xy, velocidad);
    }
}
