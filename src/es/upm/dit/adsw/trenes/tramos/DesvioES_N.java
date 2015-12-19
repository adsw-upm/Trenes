package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioES_N
        extends Desvio {
    public DesvioES_N(int cx, int cy) {
        super(new CurvaES(cx, cy), new RectoV(cx, cy));
    }
}
