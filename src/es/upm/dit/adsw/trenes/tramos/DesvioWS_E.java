package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioWS_E
        extends Desvio {
    public DesvioWS_E(int cx, int cy) {
        super(new CurvaWS(cx, cy), new RectoH(cx, cy));
    }
}
