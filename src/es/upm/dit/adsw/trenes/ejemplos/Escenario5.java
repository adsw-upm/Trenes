package es.upm.dit.adsw.trenes.ejemplos;

/**
 * @author Jose A. Manas
 * @version 21.3.2017
 */

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.awt.*;
import java.util.concurrent.Semaphore;

public class Escenario5 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = {
                "",
                "- - es h ws",
                "- es en_w h wn_e ws",
                "- v - - - v",
                "- en h h h wn -",
                ""
        };
        Terreno terreno = new Terreno(mapa);

        Tramo cambio23 = terreno.get(2, 3);
        Tramo cambio43 = terreno.get(4, 3);
        cambio23.setRecto();
        cambio43.setDesvio();

        Semaphore semaforoTunel = new Semaphore(1);
        terreno.ponSemaforoEntrada(cambio23, Enlace.N, semaforoTunel);
        terreno.ponSemaforoEntrada(cambio23, Enlace.W, semaforoTunel);
        terreno.ponSemaforoSalida(cambio23, Enlace.W, semaforoTunel);
        terreno.ponSemaforoSalida(cambio43, Enlace.N, semaforoTunel);
        terreno.ponSemaforoEntrada(cambio43, Enlace.E, semaforoTunel);

        Tren tren11 = new Tren("Talgo 1", Color.RED);
        tren11.setVelocidad(0.9);
        terreno.ponTren(1, 2, Enlace.S, tren11);

        Tren tren2 = new Tren("Expreso", Color.BLUE);
        tren2.setVelocidad(0.25);
        terreno.ponTren(5, 3, Enlace.S, tren2);

        terreno.setVisible();
    }
}