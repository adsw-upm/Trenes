package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 30/3/2012
 */
public class DesvioEN_S
        extends Desvio {
    public DesvioEN_S(int cx, int cy) {
        super(new CurvaEN(cx, cy), new RectoV(cx, cy));
    }
}
