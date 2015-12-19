package es.upm.dit.adsw.trenes.tramos;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Tren;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.concurrent.Semaphore;

/**
 * @author Jose A. Manas
 * @version 1/3/2012
 */
public abstract class Tramo {
    private static Image imageX;

    private Image imagen;
    private final int cx;
    private final int cy;
    private boolean recto;
    private boolean roto;

    private Tren tren;

    private Semaphore semaforo = new Semaphore(1);

    Tramo(int cx, int cy) {
        this.cx = cx;
        this.cy = cy;
    }

    public Image getImageX() {
        if (imageX == null)
            imageX = getImage("X.png");
        return imageX;
    }

    public int getCx() {
        return cx;
    }

    public int getCy() {
        return cy;
    }

    public void setImagen(String fichero) {
        this.imagen = getImage(fichero);
    }

    public void setImagen(Image imagen) {
        this.imagen = imagen;
    }

    public Image getImagen() {
        return imagen;
    }

    public abstract Enlace salePor(Enlace entrada);

    public abstract boolean hayEntrada(Enlace enlace);

    public abstract Enlace mueve(Enlace entrada, double[] xy, double velocidad);

    public Tren getTren() {
        return tren;
    }

    public void setRoto(boolean roto) {
        this.roto = roto;
    }

    public boolean isRoto() {
        return roto;
    }

    public void setRecto() {
        setRecto(true);
    }

    public void setRecto(boolean recto) {
        this.recto = recto;
    }

    public void setDesvio() {
        setRecto(false);
    }

    public void conmuta() {
        setRecto(!recto);
    }

    public boolean isRecto() {
        return recto;
    }

    public boolean isDesvio() {
        return !recto;
    }

    public void entra(Tren tren, Enlace entrada)
            throws InterruptedException {
        semaforo.acquire();
        this.tren = tren;
    }

    public void sale() {
        semaforo.release();
        tren = null;
    }

    public Image getImage(String fichero) {
        try {
            URL url = getClass().getResource("imgs/" + fichero);
            ImageIcon icon = new ImageIcon(url);
            return icon.getImage();
        } catch (Exception e) {
            String mensaje = "no se puede cargar "
                    + getClass().getPackage().getName()
                    + System.getProperty("file.separator")
                    + fichero;
            JOptionPane.showMessageDialog(null,
                    mensaje, "carga imagen",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    protected Image compose(Image... ii) {
        JFrame frame = null;
        int width = ii[0].getWidth(frame);
        int height = ii[1].getHeight(frame);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        for (Image i : ii)
            g.drawImage(i, 0, 0, frame);
        return bi;
    }

    @Override
    public String toString() {
        return String.format("%s en (%d, %d)", getClass().getSimpleName(), cx, cy);
    }
}
