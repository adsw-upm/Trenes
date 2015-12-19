package es.upm.dit.adsw.trenes.ui;

import es.upm.dit.adsw.trenes.Enlace;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @author Jose A. Manas
 * @version 4.1.2013
 */
public class NuevoTren
        extends JPanel
        implements ActionListener {
    private final NumberFormat format;

    private final JTextField nameField;
    private final JButton colorField;
    private final JTextField speedField;
    private final JTextField xField;
    private final JTextField yField;
    private final JComboBox entryField;

    public NuevoTren() {
        super(new SpringLayout());
        format = NumberFormat.getInstance();

        {
            JLabel label = new JLabel("nombre:");
            nameField = new JTextField();
            add(label);
            add(nameField);
        }
        {
            JLabel label = new JLabel("color:");
            colorField = new JButton();
            colorField.setBackground(Color.RED);
            colorField.addActionListener(this);
            add(label);
            add(colorField);
        }
        {
            JLabel label = new JLabel("velocidad:");
            speedField = new JTextField();
            add(label);
            add(speedField);
        }
        {
            JLabel label = new JLabel("xy:");
            Box xy = Box.createHorizontalBox();
            xField = new JTextField();
            yField = new JTextField();
            xy.add(xField);
            xy.add(yField);
            add(label);
            add(xy);
        }
        {
            JLabel label = new JLabel("entrada:");
            entryField = new JComboBox(Enlace.values());
            add(label);
            add(entryField);
        }

        SpringUtilities.makeCompactGrid(this,
                5, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        if (source == colorField) {
            Color color = JColorChooser.showDialog(this, "Color", colorField.getBackground());
            if (color == null)
                return;
            colorField.setBackground(color);
        }
    }

    public String getNombre() {
        return nameField.getText();
    }

    public Color getColor() {
        return colorField.getBackground();
    }

    public double getSpeed()
            throws ParseException {
        String text = speedField.getText().trim();
        if (text.length() == 0)
            text = "0";
        return format.parse(text).doubleValue();
    }

    public int getTramoX() {
        String text = xField.getText().trim();
        if (text.length() == 0)
            text = "0";
        return Integer.parseInt(text);
    }

    public int getTramoY() {
        String text = yField.getText().trim();
        if (text.length() == 0)
            text = "0";
        return Integer.parseInt(text);
    }

    public Enlace getEntrada() {
        return (Enlace) entryField.getSelectedItem();
    }
}
