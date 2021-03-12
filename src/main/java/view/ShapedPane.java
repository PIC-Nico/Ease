package view;

import controller.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public final class ShapedPane extends JPanel {
    private Shape shape;
    private boolean invertColors;
    private boolean importantTooltip;

    public ShapedPane() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    public void setRadius(int radius) {
        setBorder(BorderFactory.createEmptyBorder(radius, radius, radius, radius));
    }

    public void setInvertColors() {
        invertColors = true;
    }

    public void setImportantTooltip() {
        importantTooltip = true;
    }

    /**
     * This can be used to set the content pane (after the constructor w/o parameters)
     * has been finished.
     * @param contentPane The pane to set as content pane.
     */
    public void addPane(JPanel contentPane) {
        add(contentPane);
    }

    /**
     * Adds a label to the shaped pane.
     * @param label The label to add.
     */
    public void addLabel(JLabel label) {
        add(label);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color bg = Config.getInstance().getTheme().getBackground();

        if(invertColors) {
            bg = Config.getInstance().getTheme().getForeground();
        }

        if(importantTooltip) {
            bg = Config.getInstance().getTheme().getImportantTooltipBackground();
        }

        Graphics2D g2d = (Graphics2D) g.create();
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setRenderingHints(hints);
        g2d.setColor(bg);

        if(shape == null) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12);
        }

        g2d.fill(shape);
        g2d.dispose();
    }
}