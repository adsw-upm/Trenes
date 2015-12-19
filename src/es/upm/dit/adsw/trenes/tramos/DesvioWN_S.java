package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioWN_S
        extends Desvio {
    public DesvioWN_S(int cx, int cy) {
        super(new CurvaWN(cx, cy), new RectoV(cx, cy));
    }
}
