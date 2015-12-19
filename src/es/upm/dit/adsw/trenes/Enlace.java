package es.upm.dit.adsw.trenes;

/**
 * @author Jose A. Manas
 * @version 3/3/2012
 */
public enum Enlace {
    N, S, E, W;

    public Enlace opuesto() {
        switch (this) {
            case N:
                return S;
            case S:
                return N;
            case E:
                return W;
            case W:
                return E;
        }
        return null;
    }
}
