package es.upm.dit.adsw.trenes.ui;

import es.upm.dit.adsw.trenes.Enlace;
import es.upm.dit.adsw.trenes.Terreno;
import es.upm.dit.adsw.trenes.Tren;
import es.upm.dit.adsw.trenes.Version;
import es.upm.dit.adsw.trenes.tramos.Tramo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jose A. Manas
 * @version 3/1/2013
 */
public class PanelTrenes {
    private final Terreno terreno;
    private final JFrame frame;
    private JComponent centralPanel;
    private final Dimension screen;
    private final NumberFormat format;

    private Map<Tren, StartStopButton> ssbs = new HashMap<Tren, StartStopButton>();
    private Map<Tren, SpeedField> speeds = new HashMap<Tren, SpeedField>();

    public PanelTrenes(Terreno terreno) {
        this.terreno = terreno;

        format = NumberFormat.getInstance();
        screen = Toolkit.getDefaultToolkit().getScreenSize();

        frame = new JFrame(String.format("Cuadro de mando (%s)", Version.ID));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar bar = new JToolBar();
        bar.setFloatable(false);
        bar.add(new NewButton());
        bar.add(new StartAllButton());
        bar.add(new StopAllButton());
        frame.add(bar, BorderLayout.NORTH);

        actualiza();
    }

    public void actualiza() {
        if (centralPanel != null)
            frame.remove(centralPanel);
        ssbs.clear();
        Box box = Box.createVerticalBox();
        for (Tren tren : terreno.getTrenes()) {
            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 4));
            panel.add(new JLabel(tren.getNombre()));
            panel.add(new ColorField(tren));
            panel.add(new JLabel("velocidad:"));
            SpeedField speedField = new SpeedField(tren);
            speeds.put(tren, speedField);
            panel.add(speedField);
            final StartStopButton startStopButton = new StartStopButton(tren);
            ssbs.put(tren, startStopButton);
            panel.add(startStopButton);
            panel.add(new QuitarButton(tren));
            box.add(panel);
            final JSeparator separator = new JSeparator();
            separator.setMinimumSize(new Dimension(5, 5));
            box.add(separator);
        }
        centralPanel = new JScrollPane(box);
        frame.add(centralPanel, BorderLayout.CENTER);
        frame.setMinimumSize(new Dimension(200, 1));
        frame.pack();
//        frame.setLocation(screen.width / 2 - frame.getSize().width / 2, 0);
        frame.setLocation(screen.width - frame.getSize().width, 0);
        frame.setVisible(true);
    }

    public void actualiza(Tren tren) {
        StartStopButton button = ssbs.get(tren);
        if (button != null)
            button.setText();
    }

    private void setSpeed(Tren tren) {
        try {
            SpeedField speedField = speeds.get(tren);
            double velocidad = format.parse(speedField.getText()).doubleValue();
            tren.setVelocidad(velocidad);
        } catch (Exception ignored) {
        }
    }

    private class SpeedField
            extends JTextField
            implements ActionListener {
        private final Tren tren;

        public SpeedField(Tren tren) {
            this.tren = tren;
            setText(format.format(tren.getVelocidad()));
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent event) {
            setSpeed(tren);
        }
    }

    private class ColorField
            extends JButton
            implements ActionListener {
        private final Tren tren;

        public ColorField(Tren tren) {
            this.tren = tren;
            setBackground(tren.getColor());
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent event) {
            Color color = JColorChooser.showDialog(this, "Color", tren.getColor());
            if (color == null)
                return;
            tren.setColor(color);
            setBackground(color);
        }
    }

    private class NewButton
            extends AbstractAction {
        private NewButton() {
            super("nuevo");
        }

        public void actionPerformed(ActionEvent event) {
            NuevoTren panel = new NuevoTren();
            while (true) {
                try {
                    int result = JOptionPane.showConfirmDialog(null,
                            panel, "nuevo tren",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                    if (result != JOptionPane.OK_OPTION)
                        return;
                    int x = panel.getTramoX();
                    int y = panel.getTramoY();
                    Tramo tramo = terreno.get(x, y);
                    if (tramo == null)
                        throw new IllegalArgumentException(
                                String.format("no hay via en: (%d, %d)", x, y));
                    if (tramo.getTren() != null)
                        throw new IllegalArgumentException(
                                String.format("tramo ocupado: (%d, %d)", x, y));
                    Enlace entrada = panel.getEntrada();
                    if (!tramo.hayEntrada(entrada))
                        throw new IllegalArgumentException("no existe esa entrada: " + entrada);

                    Tren tren = new Tren(panel.getNombre(), panel.getColor());
                    tren.setVelocidad(panel.getSpeed());
                    terreno.ponTren(x, y, entrada, tren);

                    actualiza();
                    return;
                } catch (Exception e) {
//                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            e.getMessage(), "nuevo tren",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private class StartAllButton
            extends AbstractAction {
        private StartAllButton() {
            super("arracar todos");
        }

        public void actionPerformed(ActionEvent e) {
            for (Tren tren : terreno.getTrenes()) {
                setSpeed(tren);
                tren.arrancar();
            }
        }
    }

    private class StopAllButton
            extends AbstractAction {
        private StopAllButton() {
            super("parar todos");
        }

        public void actionPerformed(ActionEvent e) {
            for (Tren tren : terreno.getTrenes())
                tren.parar();
        }
    }

    private class StartStopButton
            extends JButton
            implements ActionListener {
        private final Tren tren;

        public StartStopButton(Tren tren) {
            super();
            this.tren = tren;
            setText();
            addActionListener(this);
        }

        private void setText() {
            setText(tren.isParado() ? "arrancar" : "parar");
        }

        public void actionPerformed(ActionEvent e) {
            setSpeed(tren);
            if (tren.isParado())
                tren.arrancar();
            else
                tren.parar();
        }
    }

    private class QuitarButton
            extends JButton
            implements ActionListener {
        private final Tren tren;

        public QuitarButton(Tren tren) {
            super("quitar");
            this.tren = tren;
            addActionListener(this);
        }

        public void actionPerformed(ActionEvent e) {
            terreno.quitaTren(tren);
            actualiza();
        }
    }

}
