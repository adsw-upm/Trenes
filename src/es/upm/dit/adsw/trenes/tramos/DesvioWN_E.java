package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioWN_E
        extends Desvio {
    public DesvioWN_E(int cx, int cy) {
        super(new CurvaWN(cx, cy), new RectoH(cx, cy));
    }
}
