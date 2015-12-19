package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioES_W
        extends Desvio {
    public DesvioES_W(int cx, int cy) {
        super(new CurvaES(cx, cy), new RectoH(cx, cy));
    }
}
