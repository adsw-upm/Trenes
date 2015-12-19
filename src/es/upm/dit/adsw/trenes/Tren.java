package es.upm.dit.adsw.trenes;

import es.upm.dit.adsw.trenes.tramos.Tramo;

import java.awt.*;

/**
 * @author Jose A. Manas
 * @version 2/4/2012
 */
public class Tren
        extends Thread {
    private final String nombre;
    private Color color;

    private Terreno terreno;

    private volatile boolean activo = true;
    private volatile boolean parado = true;
    private volatile double velocidad;

    private Tramo tramo;
    private Enlace entrada;
    private double[] xy = new double[2];

    public Tren(String nombre, Color color) {
        this.nombre = nombre;
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    void set(Terreno terreno, Tramo tramo, Enlace entrada) {
        this.terreno = terreno;
        if (!tramo.hayEntrada(entrada))
            throw new IllegalArgumentException(tramo + " sin entrada " + entrada);
        if (tramo.getTren() != null)
            throw new IllegalArgumentException(tramo + " ocupado");
        try {
            tramo.entra(this, entrada);
            setEntrada(tramo, entrada);
        } catch (InterruptedException ignored) {
        }
    }

    public void setEntrada(Tramo tramo, Enlace entrada) {
        this.tramo = tramo;
        this.entrada = entrada;
        if (entrada == Enlace.N)
            setXY(0.5, 1);
        else if (entrada == Enlace.S)
            setXY(0.5, 0);
        else if (entrada == Enlace.E)
            setXY(1, 0.5);
        else if (entrada == Enlace.W)
            setXY(0, 0.5);
    }

    private void setXY(double x, double y) {
        xy[0] = x;
        xy[1] = y;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public double getDx() {
        return xy[0];
    }

    public double getDy() {
        return xy[1];
    }

    public Tramo getTramo() {
        return tramo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void parar() {
        setParado(true);
    }

    public void arrancar() {
        setParado(false);
    }

    public void setParado(boolean parado) {
        this.parado = parado;
        terreno.actualiza(this);
    }

    public boolean isParado() {
        return parado;
    }

    @Override
    public void run() {
        while (activo) {
            try {
                if (!parado && velocidad > 0) {
                    int times = 1;
                    double dt = velocidad;
                    if (velocidad > 0.5) {
                        times = (int) Math.ceil(velocidad / 0.5);
                        dt = velocidad / times;
                    }
                    for (int t = 0; t < times; t++) {
                        if (parado)
                            break;
                        Enlace salida = tramo.mueve(entrada, xy, dt);
                        if (salida != null)
                            salgo(tramo, salida);
                    }
                }
                terreno.pintame();
                Thread.sleep(50);
            } catch (InterruptedException ignored) {
            }
        }
        tramo.sale();
    }

    private void salgo(Tramo tramo1, Enlace salida)
            throws InterruptedException {
        Tramo tramo2 = terreno.siguiente(tramo1, salida);
        Enlace entrada = salida.opuesto();

        terreno.entro(this, tramo2, entrada);
        terreno.salgo(this, tramo1, salida);

        if (tramo2 == null || !tramo2.hayEntrada(entrada)) {
            parar();
            setEntrada(tramo1, salida);
        } else {
            tramo2.entra(this, entrada);
            setEntrada(tramo2, entrada);
            tramo1.sale();
        }
    }

    @Override
    public String toString() {
        return nombre;
    }
}
