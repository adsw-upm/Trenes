package es.upm.dit.adsw.trenes.ejemplos.filosofos;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Monitor;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;

import java.awt.*;

/**
 * El problema de los filosofos.
 *
 * @author Jose A. Manas
 * @version 4.12.2015
 */
public class Escenario {

    /**
     * Metodo principal para arrancar desde consola.
     *
     * @param args No utiliza argumentos.
     */
    public static void main(String[] args) {
        String[] mapa = new String[]{
                "- - se h ws",
                "se h ws_n - v",
                "v - ne_s es_w nw_e ws",
                "ne es_w nw_e ws_n - v",
                "- v - ne_s h wn",
                "- ne h wn - -"
        };
        Terreno terreno = new Terreno(mapa);

        Monitor monitor = new Controlador(4);
        terreno.ponMonitorEntrada(2, 4, Enlace.W, monitor, 0);
        terreno.ponMonitorSalida(1, 2, Enlace.W, monitor, 0);
        terreno.ponMonitorEntrada(4, 3, Enlace.N, monitor, 1);
        terreno.ponMonitorSalida(2, 4, Enlace.N, monitor, 1);
        terreno.ponMonitorEntrada(3, 1, Enlace.E, monitor, 2);
        terreno.ponMonitorSalida(4, 3, Enlace.E, monitor, 2);
        terreno.ponMonitorEntrada(1, 2, Enlace.S, monitor, 3);
        terreno.ponMonitorSalida(3, 1, Enlace.S, monitor, 3);

        Tren filo1 = new Tren("filosofo 1", Color.RED);
        filo1.setVelocidad(1);
        terreno.ponTren(0, 3, Enlace.S, filo1);

        Tren filo2 = new Tren("filosofo 2", Color.GREEN);
        filo2.setVelocidad(0.9);
        terreno.ponTren(3, 5, Enlace.W, filo2);

        Tren filo3 = new Tren("filosofo 3", Color.BLUE);
        filo3.setVelocidad(0.8);
        terreno.ponTren(5, 2, Enlace.N, filo3);

        Tren filo4 = new Tren("filosofo 4", Color.YELLOW);
        filo4.setVelocidad(0.7);
        terreno.ponTren(2, 0, Enlace.E, filo4);

        terreno.setVisible();
    }
}