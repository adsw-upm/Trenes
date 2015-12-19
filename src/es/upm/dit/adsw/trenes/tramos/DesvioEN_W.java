package es.upm.dit.adsw.trenes.tramos;

/**
 * @author Jose A. Manas
 * @version 8/3/2012
 */
public class DesvioEN_W
        extends Desvio {
    public DesvioEN_W(int cx, int cy) {
        super(new CurvaEN(cx, cy), new RectoH(cx, cy));
    }
}
