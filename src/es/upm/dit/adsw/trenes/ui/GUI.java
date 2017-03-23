package es.upm.dit.adsw.trenes.ui;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.TES;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Semaphore;

public class GUI
        extends JPanel {
    public static final int TREN_RADIO = 13;
    private static final int SEM_DIAM = 12;

    private static final int MARGEN = 10;
    private static final int celdaX = 100;
    private static final int celdaY = 100;

    private int ANCHO;
    private int ALTO;

    private Terreno terreno;

    private void nuevoJuego(Terreno terreno) {
        this.terreno = terreno;
        ANCHO = MARGEN + celdaX * terreno.getDimX() + MARGEN;
        ALTO = MARGEN + celdaY * terreno.getDimY() + MARGEN;
        pintame();
    }

    private GUI(Container container, Terreno terreno) {
        nuevoJuego(terreno);

        setPreferredSize(new Dimension(ANCHO, ALTO));
        container.add(this, BorderLayout.CENTER);
        setFocusable(true);
        requestFocusInWindow();
        addMouseListener(new MyMouseListener());

        repaint();
    }

    /**
     * Constructor.
     *
     * @param frame Pantalla en consola.
     */
    public GUI(JFrame frame, Terreno terreno) {
        this(frame.getContentPane(), terreno);
    }

    public void pintame() {
        repaint();
    }

    /**
     * Llamada por java para pintarse en la pantalla.
     *
     * @param g sistema grafico 2D para dibujarse.
     */
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        int nwx = MARGEN;
        int nwy = MARGEN;
        int ladoX = terreno.getDimX();
        int ladoY = terreno.getDimY();

        // rejilla
        g.setColor(Color.LIGHT_GRAY);
        for (int x = 0; x <= ladoX; x++)
            g.drawLine(MARGEN + x * celdaX, MARGEN, MARGEN + x * celdaX, ALTO - MARGEN);
        for (int y = 0; y <= ladoY; y++)
            g.drawLine(MARGEN, MARGEN + y * celdaY, ANCHO - MARGEN, MARGEN + y * celdaY);

        // marco
        g.setColor(Color.BLACK);
        int ancho = ladoX * celdaX;
        int alto = ladoY * celdaY;
        g.drawLine(nwx - 1, nwy - 1, nwx - 1, nwy + alto + 1);
        g.drawLine(nwx + ancho + 1, nwy - 1, nwx + ancho + 1, nwy + alto + 1);
        g.drawLine(nwx - 1, nwy - 1, nwx + ancho + 1, nwy - 1);
        g.drawLine(nwx - 1, nwy + alto + 1, nwx + ancho + 1, nwy + alto + 1);

        // tramos
        for (int x = 0; x < ladoX; x++) {
            for (int y = 0; y < ladoY; y++)
                pintaCelda(g, x, y);
        }

        // semaforos
        for (TES te : terreno.getSemaforosEntradas()) {
            Tramo tramo = te.tramo;
            Enlace enlace = te.enlace;
            Semaphore semaforo = te.semaforo;
            nwx = getSemaforoNWX(tramo, enlace);
            nwy = getSemaforoNWY(tramo, enlace);
            Color color = semaforo.availablePermits() == 0 ? Color.RED : Color.GREEN;
            g.setColor(color);
            g.fillOval(nwx, nwy, SEM_DIAM, SEM_DIAM);
            g.setColor(Color.BLACK);
            g.drawOval(nwx, nwy, SEM_DIAM, SEM_DIAM);
        }
        for (TES te : terreno.getSemaforosSalidas()) {
            Tramo tramo = te.tramo;
            Enlace enlace = te.enlace;
            Semaphore semaforo = te.semaforo;
            nwx = getSemaforoNWX(tramo, enlace);
            nwy = getSemaforoNWY(tramo, enlace);
            Color color = Color.WHITE;
            g.setColor(Color.BLACK);
            g.drawRect(nwx - 1, nwy - 1, SEM_DIAM + 2, SEM_DIAM + 2);
        }

        // trenes
        for (Tren tren : terreno.getTrenes())
            pintaTren(g, tren);
    }

    /**
     * Contenido de 1 celda.
     *
     * @param g sistema grafico 2D para dibujarse.
     * @param x columna.
     * @param y fila.
     */
    private void pintaCelda(Graphics g, int x, int y) {
        Tramo tramo = terreno.get(x, y);
        if (tramo == null)
            return;
        int wx = wCx(x);
        int ny = nCy(y);
        g.drawImage(tramo.getImagen(), wx, ny, null);
        if (tramo.isRoto())
            g.drawImage(tramo.getImageX(), wx, ny, null);
    }

    /**
     * Pinta un tren en sus coordenadas actuales.
     *
     * @param g    sistema grafico 2D para dibujarse.
     * @param tren tren.
     */
    private void pintaTren(Graphics g, Tren tren) {
        Tramo tramo = tren.getTramo();
        if (tramo == null)
            return;
        int cx = tramo.getCx();
        int cy = tramo.getCy();

        double dx = tren.getDx();
        double dy = tren.getDy();

        double tx = wCx(cx) + dx * celdaX;
        double ty = sCy(cy) - dy * celdaY;

        g.setColor(tren.getColor());
        int radio = TREN_RADIO;
        int diametro = radio * 2;
        g.fillOval((int) (tx - radio), (int) (ty - radio), diametro, diametro);
    }

    /**
     * @param tramo  tramo en el que se encuentra el semafono.
     * @param enlace enlace (del tramo) en el que se encuentra el semaforo.
     * @return coordenada X de la esquina noroeste.
     */
    private int getSemaforoNWX(Tramo tramo, Enlace enlace) {
        if (enlace == Enlace.N)
            return wCx(tramo.getCx()) + 2;
        else if (enlace == Enlace.S)
            return eCx(tramo.getCx()) - SEM_DIAM - 2;
        else if (enlace == Enlace.E)
            return eCx(tramo.getCx()) - SEM_DIAM - 2;
        else
            return wCx(tramo.getCx()) + 2;
    }

    /**
     * @param tramo  tramo en el que se encuentra el semafono.
     * @param enlace enlace (del tramo) en el que se encuentra el semaforo.
     * @return coordenada Y de la esquina noroeste.
     */
    private int getSemaforoNWY(Tramo tramo, Enlace enlace) {
        if (enlace == Enlace.N)
            return nCy(tramo.getCy()) + 2;
        else if (enlace == Enlace.S)
            return sCy(tramo.getCy()) - SEM_DIAM - 2;
        else if (enlace == Enlace.E)
            return nCy(tramo.getCy()) + 2;
        else
            return sCy(tramo.getCy()) - SEM_DIAM - 2;
    }

    private int nCx(int columna) {
        return cCx(columna);
    }

    private int sCx(int columna) {
        return cCx(columna);
    }

    private int eCx(int columna) {
        return MARGEN + columna * celdaX + celdaX;
    }

    private int wCx(int columna) {
        return MARGEN + columna * celdaX;
    }

    private int cCx(int columna) {
        return wCx(columna) + celdaX / 2;
    }

    private int nCy(int fila) {
        int lado = terreno.getDimY();
        return MARGEN + (lado - fila - 1) * celdaY;
    }

    private int sCy(int fila) {
        int lado = terreno.getDimY();
        return MARGEN + (lado - fila) * celdaY;
    }

    private int eCy(int fila) {
        return cCy(fila);
    }

    private int wCy(int fila) {
        return cCy(fila);
    }

    private int cCy(int fila) {
        return nCy(fila) + celdaY / 2;
    }

    /**
     * Cosas que hace el raton.
     */
    private class MyMouseListener
            extends MouseAdapter {

        /**
         * Captura los clics sobre el tablero.
         *
         * @param event accion del raton.
         */
        public void mouseClicked(MouseEvent event) {
            requestFocusInWindow();
            if (event.getX() < MARGEN || event.getY() < MARGEN)
                return;
            int x = (event.getX() - MARGEN) / celdaX;
            int y = terreno.getDimY() - 1 - ((event.getY() - MARGEN) / celdaY);
            Tramo tramo = terreno.get(x, y);
            if (tramo == null)
                return;
            tramo.conmuta();
            repaint();
        }
    }
}
