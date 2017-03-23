package es.upm.dit.adsw.trenes;

import es.upm.dit.adsw.trenes.tramos.*;
import es.upm.dit.adsw.trenes.ui.GUI;
import es.upm.dit.adsw.trenes.ui.PanelTrenes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author Jose A. Manas
 * @version 21.3.2017
 */
public class Terreno {
    private final Tramo[][] mapa;
    private final int dimX;
    private final int dimY;

    private final List<Tren> trenes = new ArrayList<Tren>();
    private final List<TES> semaforosEntradas = new ArrayList<TES>();
    private final List<TES> semaforosSalidas = new ArrayList<TES>();
    private final List<TEM> monitoresEntradas = new ArrayList<TEM>();
    private final List<TEM> monitoresSalidas = new ArrayList<TEM>();

    private JFrame frame;
    private GUI gui;
    private PanelTrenes panelTrenes;

    public Terreno(int dx, int dy) {
        this.dimX = dx;
        this.dimY = dy;
        mapa = new Tramo[dx][dy];
    }

    public Terreno(String[] mapa) {
        this.dimY = mapa.length;
        String[][] mapa2d = new String[dimY][];
        int dx = 0;
        for (int y = 0; y < mapa.length; y++) {
            mapa2d[y] = mapa[y].trim().split("\\s+");
            dx = Math.max(dx, mapa2d[y].length);
        }
        this.dimX = dx;
        this.mapa = new Tramo[this.dimX][this.dimY];
        for (int y = 0; y < this.dimY; y++) {
            final String[] fila = mapa2d[this.dimY - 1 - y];
            if (fila.length == 0)
                continue;
            int x = 0;
            for (String s : fila) {
                if (s.length() == 0)
                    continue;

                if (match(s, "-", "_"))
                    x++;

                else {
                    if (match(s, "h"))
                        set(new RectoH(x++, y));
                    else if (match(s, "v"))
                        set(new RectoV(x++, y));

                    else if (match(s, "he"))
                        set(new RectoH_E(x++, y));
                    else if (match(s, "ve"))
                        set(new RectoV_E(x++, y));

                    else if (match(s, "c", "+"))
                        set(new Cruce(x++, y));

                    else if (match(s, "es", "se"))
                        set(new CurvaES(x++, y));
                    else if (match(s, "en", "ne"))
                        set(new CurvaEN(x++, y));
                    else if (match(s, "ws", "sw"))
                        set(new CurvaWS(x++, y));
                    else if (match(s, "wn", "nw"))
                        set(new CurvaWN(x++, y));

                    else if (match(s, "es_n", "se_n", "v_es", "v_se"))
                        set(new DesvioES_N(x++, y));
                    else if (match(s, "es_w", "se_w", "h_se", "h_es"))
                        set(new DesvioES_W(x++, y));
                    else if (match(s, "en_s", "ne_s", "v_en", "v_ne"))
                        set(new DesvioEN_S(x++, y));
                    else if (match(s, "en_w", "ne_w", "h_wn", "h_nw"))
                        set(new DesvioEN_W(x++, y));
                    else if (match(s, "ws_n", "sw_n", "v_ws", "v_sw"))
                        set(new DesvioWS_N(x++, y));
                    else if (match(s, "ws_e", "sw_e", "h_ws", "h_sw"))
                        set(new DesvioWS_E(x++, y));
                    else if (match(s, "wn_s", "nw_s", "v_wn", "v_nw"))
                        set(new DesvioWN_S(x++, y));
                    else if (match(s, "wn_e", "nw_e", "h_wn", "h_nw"))
                        set(new DesvioWN_E(x++, y));

                    else {
                        String mensaje = "no entiendo: " + s;
                        String title = "new Terreno()";
                        JOptionPane.showMessageDialog(null,
                                mensaje, title,
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private boolean match(String s, String... claves) {
        for (String clave : claves) {
            if (s.equalsIgnoreCase(clave))
                return true;
        }
        return false;
    }

    private Tramo set(Tramo tramo) {
        int cx = tramo.getCx();
        int cy = tramo.getCy();
        mapa[cx][cy] = tramo;
        return tramo;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public Tramo get(int x, int y) {
        return mapa[x][y];
    }

    public Tramo siguiente(Tramo actual, Enlace salida) {
        try {
            int cx = actual.getCx();
            int cy = actual.getCy();
            switch (salida) {
                case N:
                    return get(cx, cy + 1);
                case S:
                    return get(cx, cy - 1);
                case E:
                    return get(cx + 1, cy);
                case W:
                    return get(cx - 1, cy);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public void ponTren(int x, int y, Enlace enlace, Tren tren) {
        if (tren == null)
            return;
        Tramo tramo = get(x, y);
        if (tramo == null) {
            String mensaje = String.format("no hay vía (%d, %d)", x, y);
            String title = "ponTren()";
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (tramo.getTren() != null && tramo.getTren() != tren) {
            String mensaje = String.format("ya hay un tren en (%d, %d)", x, y);
            String title = "ponTren()";
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        synchronized (trenes) {
            tren.set(this, tramo, enlace);
            trenes.add(tren);
        }
        tren.start();
    }

    public void quitaTren(Tren tren) {
        tren.setActivo(false);
        Tramo tramo = tren.getTramo();
        if (tramo != null)
            tramo.sale();
        synchronized (trenes) {
            trenes.remove(tren);
        }
    }

    public Collection<Tren> getTrenes() {
        synchronized (trenes) {
            return trenes;
        }
    }

    /**
     * Se coloca un semaforo en la entrada del tramo y enlace indicados.
     * El semaforo se pinta segun se entra a la derecha.
     * El semaforo recive una llamada P cuando entren entra por ese enlace.
     *
     * @param tramo    tramo en el que se pone el semaforo.
     * @param enlace   enlace del tramo por el que se considera el semaforo.
     * @param semaforo el semaforo que controla entradas y salidas por ese punto.
     */
    public void ponSemaforoEntrada(Tramo tramo, Enlace enlace, Semaphore semaforo) {
        semaforosEntradas.add(new TES(tramo, enlace, semaforo));
    }

    /**
     * Se coloca un semaforo en la salida del tramo y enlace indicados.
     * Hablando con precision no es una luz, sino un sensor-
     * El sensor se pinta segun se entra a la derecha, como un cuadrado vacio.
     * El semaforo recibe una llamada V cuando el tren sale por ese enlace.
     *
     * @param tramo    tramo en el que se pone el semaforo.
     * @param enlace   enlace del tramo por el que se considera el semaforo.
     * @param semaforo el semaforo que controla entradas y salidas por ese punto.
     */
    public void ponSemaforoSalida(Tramo tramo, Enlace enlace, Semaphore semaforo) {
        semaforosSalidas.add(new TES(tramo, enlace, semaforo));
    }

    public Collection<TES> getSemaforosEntradas() {
        return semaforosEntradas;
    }

    public Collection<TES> getSemaforosSalidas() {
        return semaforosSalidas;
    }

    public void setVisible() {
        if (frame == null) {
            frame = new JFrame(String.format("Trenes (%s)", Version.ID));
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            gui = new GUI(frame, this);
            panelTrenes = new PanelTrenes(this);
        }
        frame.pack();
        frame.setVisible(true);
    }

    public void pintame() {
        if (gui != null)
            gui.pintame();
    }

    public void actualiza(Tren tren) {
        if (panelTrenes != null)
            panelTrenes.actualiza(tren);
    }

    /**
     * Pone un sensor en un enlace de entrada de un tramo y lo asocia a un monitor con un tag.
     *
     * @param x       coordenada X del tramo que se monitoriza.
     * @param y       coordenada Y del tramo que se monitoriza.
     * @param enlace  se monitorizan las entradas por este enlace.
     * @param monitor monitor que atiende a los trenes que entran.
     * @param tag     pista para el monitor.
     */
    public void ponMonitorEntrada(int x, int y, Enlace enlace, Monitor monitor, int tag) {
        Tramo tramo = get(x, y);
        if (tramo == null) {
            String mensaje = String.format("no hay vía (%d, %d)", x, y);
            String title = String.format("ponMonitorEntrada(%s)", monitor.getClass().getSimpleName());
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ponMonitorEntrada(tramo, enlace, monitor, tag);
    }

    /**
     * Pone un sensor en un enlace de salida de un tramo y lo asocia a un monitor con un tag.
     *
     * @param x       coordenada X del tramo que se monitoriza.
     * @param y       coordenada Y del tramo que se monitoriza.
     * @param enlace  se monitorizan las salidas por este enlace.
     * @param monitor monitor que atiende a los trenes que salen.
     * @param tag     pista para el monitor.
     */
    public void ponMonitorSalida(int x, int y, Enlace enlace, Monitor monitor, int tag) {
        Tramo tramo = get(x, y);
        if (tramo == null) {
            String mensaje = String.format("no hay vía (%d, %d)", x, y);
            String title = String.format("ponMonitorSalida(%s)", monitor.getClass().getSimpleName());
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        ponMonitorSalida(tramo, enlace, monitor, tag);
    }

    /**
     * Pone un sensor en un enlace de entrada de un tramo y lo asocia a un monitor con un tag.
     *
     * @param tramo   tramo que se monitoriza.
     * @param enlace  se monitorizan las entradas por este enlace.
     * @param monitor monitor que atiende a los trenes que entran.
     * @param tag     pista para el monitor.
     */
    public void ponMonitorEntrada(Tramo tramo, Enlace enlace, Monitor monitor, int tag) {
        if (!tramo.hayEntrada(enlace)) {
            String mensaje = String.format("%s: no hay enlace %s", tramo, enlace);
            String title = String.format("ponMonitorEntrada(%s)", monitor.getClass().getSimpleName());
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        monitoresEntradas.add(new TEM(tramo, enlace, monitor, tag));
    }

    /**
     * Pone un sensor en un enlace de salida de un tramo y lo asocia a un monitor con un tag.
     *
     * @param tramo   tramo que se monitoriza.
     * @param enlace  se monitorizan las salidas por este enlace.
     * @param monitor monitor que atiende a los trenes que salen.
     * @param tag     pista para el monitor.
     */
    public void ponMonitorSalida(Tramo tramo, Enlace enlace, Monitor monitor, int tag) {
        if (!tramo.hayEntrada(enlace)) {
            String mensaje = String.format("%s: no hay enlace %s", tramo, enlace);
            String title = String.format("ponMonitorSalida(%s)", monitor.getClass().getSimpleName());
            JOptionPane.showMessageDialog(null,
                    mensaje, title,
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        monitoresSalidas.add(new TEM(tramo, enlace, monitor, tag));
    }

    public void entro(Tren tren, Tramo tramo, Enlace entrada) {
        if (tramo == null || entrada == null)
            return;
        for (TES tes : semaforosEntradas) {
            if (tes.tramo.equals(tramo) && tes.enlace.equals(entrada))
                try {
                    tes.semaforo.acquire();
                } catch (InterruptedException ignored) {
                }
        }
        for (TEM tem : monitoresEntradas) {
            if (tem.tramo.equals(tramo) && tem.enlace.equals(entrada))
                tem.monitor.entro(tem.tag, tren, tramo, entrada);
        }
    }

    public void salgo(Tren tren, Tramo tramo, Enlace salida) {
        if (tramo == null || salida == null)
            return;
        for (TEM tem : monitoresSalidas) {
            if (tem.tramo.equals(tramo) && tem.enlace.equals(salida))
                tem.monitor.salgo(tem.tag, tren, tramo, salida);
        }
        for (TES tes : semaforosSalidas) {
            if (tes.tramo.equals(tramo) && tes.enlace.equals(salida))
                tes.semaforo.release();
        }
    }
}
