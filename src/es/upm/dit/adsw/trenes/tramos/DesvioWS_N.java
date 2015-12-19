package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioWS_N
        extends Desvio {
    public DesvioWS_N(int cx, int cy) {
        super(new CurvaWS(cx, cy), new RectoV(cx, cy));
    }
}
