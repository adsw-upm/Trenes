package es.upm.dit.adsw.trenes.ejemplos;

/**
 * @author Jose A. Manas
 * @version 21.3.2017
 */

import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.awt.*;
import java.util.concurrent.Semaphore;

import static es.upm.dit.adsw.trenes.Enlace.*;

public class Escenario6 {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = new String[]{
                "",
                " se  h sw",
                " ne  h en_w  h ws_e  h sw",
                "  -  -  -  -   ne    h wn",
                ""
        };
        Terreno terreno = new Terreno(mapa);

        Tramo aguja22 = terreno.get(2, 2);
        Tramo aguja42 = terreno.get(4, 2);
        aguja22.setDesvio();
        aguja42.setDesvio();

        Semaphore semaforoTunel = new Semaphore(1);
        terreno.ponSemaforoEntrada(aguja22, W, semaforoTunel);
        terreno.ponSemaforoSalida(aguja42, S, semaforoTunel);
        terreno.ponSemaforoEntrada(aguja42, E, semaforoTunel);
        terreno.ponSemaforoSalida(aguja22, N, semaforoTunel);

        Tren tren11 = new Tren("Talgo 1", Color.RED);
        tren11.setVelocidad(0.9);
        terreno.ponTren(1, 2, W, tren11);

        Tren tren2 = new Tren("Expreso", Color.BLUE);
        tren2.setVelocidad(0.8);
        terreno.ponTren(5, 2, E, tren2);

        terreno.setVisible();
    }
}