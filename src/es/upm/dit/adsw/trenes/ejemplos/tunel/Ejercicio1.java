package es.upm.dit.adsw.trenes.ejemplos.tunel;

/**
 * @author Jose A. Manas
 * @version 4.12.2015
 */

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.awt.*;

public class Ejercicio1 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = new String[]{
                "- - se h h h ws",
                "- - v - - - v",
                "- se ne_w h h ws_e wn",
                "- v - - - v",
                "- ne h h h wn",
                ""
        };
        Terreno terreno = new Terreno(mapa);

        Tramo agujas01 = terreno.get(2, 3);
        Tramo agujas02 = terreno.get(5, 3);

        agujas01.setRecto();
        agujas02.setRecto();

//        Monitor tunel = new MonitorTunel();
//        Monitor tunel = new MonitorTunel_1();
//        Monitor tunel = new MonitorTunel_2();
//        Monitor tunel = new MonitorTunel_3();
        Monitor tunel = new MonitorTunel_4();

        terreno.ponMonitor(agujas01, Enlace.N, tunel, 1);
        terreno.ponMonitor(agujas02, Enlace.E, tunel, 1);
        terreno.ponMonitor(agujas02, Enlace.S, tunel, 2);
        terreno.ponMonitor(agujas01, Enlace.W, tunel, 2);

        Tren tren11 = new Tren("Talgo 1", Color.RED);
        tren11.setVelocidad(1.0);
        terreno.ponTren(2, 4, Enlace.N, tren11);

        Tren tren12 = new Tren("Talgo 2", Color.ORANGE);
        tren12.setVelocidad(0.95);
        terreno.ponTren(3, 5, Enlace.E, tren12);

        Tren tren13 = new Tren("Talgo 3", Color.YELLOW);
        tren13.setVelocidad(0.9);
        terreno.ponTren(5, 5, Enlace.E, tren13);

        Tren tren14 = new Tren("Talgo 4", Color.GREEN);
        tren14.setVelocidad(0.8);
        terreno.ponTren(6, 4, Enlace.S, tren14);

        Tren tren21 = new Tren("Expreso1", Color.BLUE);
        tren21.setVelocidad(0.5);
        terreno.ponTren(5, 1, Enlace.W, tren21);

//        Tren tren22 = new Tren("Expreso2", Color.CYAN);
//        tren22.setVelocidad(0.4);
//        terreno.ponTren(4, 1, Enlace.W, tren22);

        terreno.setVisible();
    }
}