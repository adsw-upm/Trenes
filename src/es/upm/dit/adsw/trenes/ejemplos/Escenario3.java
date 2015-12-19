package es.upm.dit.adsw.trenes.ejemplos;

/**
 * @author Jose A. Manas
 * @version 11/2/2012
 */

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;

import java.awt.*;

public class Escenario3 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = {
                "- - es h ws",
                "- es + ws v",
                "- v en + wn",
                "- en h wn",
                ""
        };
        Terreno terreno = new Terreno(mapa);

        Tren tren1 = new Tren("Talgo", Color.RED);
        tren1.setVelocidad(0.9);
        terreno.ponTren(1, 2, Enlace.N, tren1);

        Tren tren2 = new Tren("Expreso", Color.BLUE);
        tren2.setVelocidad(0.5);
        terreno.ponTren(4, 3, Enlace.N, tren2);

        terreno.setVisible();
    }
}