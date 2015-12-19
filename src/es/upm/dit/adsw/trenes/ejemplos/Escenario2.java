package es.upm.dit.adsw.trenes.ejemplos;

/**
 * @author Jose A. Manas
 * @version 11/2/2012
 */

import es.upm.dit.adsw.trenes.*;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.awt.*;

public class Escenario2 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = {
                "- es h ws",
                "es en_w h wn_e ws",
                "v - - - v",
                "en h h h wn"
        };
        Terreno terreno = new Terreno(mapa);

        Tramo cambio23 = terreno.get(1, 2);
        Tramo cambio43 = terreno.get(3, 2);
        cambio23.setRecto();
        cambio43.setDesvio();

        Monitor tunel = new MonitorTunel();
        terreno.ponMonitor(cambio23, Enlace.W, tunel, 1);
        terreno.ponMonitor(cambio23, Enlace.N, tunel, 1);
        terreno.ponMonitor(cambio43, Enlace.N, tunel, 1);
        terreno.ponMonitor(cambio43, Enlace.E, tunel, 2);
        terreno.ponMonitor(cambio23, Enlace.W, tunel, 2);

        Tren tren11 = new Tren("Talgo 1", Color.RED);
        tren11.setVelocidad(0.9);
        terreno.ponTren(0, 1, Enlace.S, tren11);
//        tren11.set(terreno, 1, 2, Enlace.S);
//        tren11.start();

        Tren tren12 = new Tren("Talgo 2", Color.ORANGE);
        tren12.setVelocidad(0.9);
        terreno.ponTren(1, 0, Enlace.E, tren12);
//        tren12.set(terreno, 2, 1, Enlace.E);
//        tren12.start();

        Tren tren13 = new Tren("Talgo 3", Color.GREEN);
        tren13.setVelocidad(0.9);
        terreno.ponTren(3, 0, Enlace.E, tren13);
//        tren13.set(terreno, 4, 1, Enlace.E);
//        tren13.start();

        Tren tren2 = new Tren("Expreso", Color.BLUE);
        tren2.setVelocidad(0.25);
        terreno.ponTren(4, 2, Enlace.S, tren2);
//        tren2.set(terreno, 5, 3, Enlace.S);
//        tren2.start();

        terreno.setVisible();
    }
}