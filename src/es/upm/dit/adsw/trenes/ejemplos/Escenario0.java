package es.upm.dit.adsw.trenes.ejemplos;

import es.upm.dit.adsw.trenes.Terreno;

/**
 * @author Jose A. Manas
 * @version 3/1/2013
 */

public class Escenario0 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = {
                "",
                "  - se  h h ws -",
                "  -  v  - -  v -",
                "  - ne  h h nw -",
                ""
        };
        Terreno terreno = new Terreno(mapa);
        terreno.setVisible();
    }
}