package view;

import controller.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class Tooltip extends JFrame implements ActionListener {
    private ShapedPane shapedPane;
    private final JPanel parentTitle;
    private final JLabel label = new JLabel();
    private Component component;
    private final Dimension screenSize;
    private Timer tmrA;
    private Timer tmrB;
    private float opacity;

    private boolean center;
    private boolean isImportant;

    private final int radius = 2;

    // vertical gap between tooltip and component
    private final int VGAP = 8;

    // steps opacity will go to 100% transparent
    private final float opacitySteps = 0.02f;

    // milli seconds between each transparency step
    private final int eventTime = 10;

    // will be used to get the location of a mouse click
    private Point click;

    public Tooltip(JPanel parentTitle) {
        this.parentTitle = parentTitle;

        // make the frame undecorated and transparent (necessary to get round corners)
        setUndecorated(true);
        setBackground(new Color(0,0,0,0));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setFocusableWindowState(false);
        setLocationRelativeTo(null);

        /*
        The screen size will be used to check whether the tooltip can be displayed right next to the parent component or
        if it is necessary to switch to the left position (left to the parent component).
         */
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // set the foreground color (note: inverted)
        label.setForeground(Config.getInstance().getTheme().getBackground());
        label.setFont(Config.getInstance().getNormalFont());
        label.setBorder(new EmptyBorder(2, 4, 2, 4));

        initShapedPane();
    }

    /**
     * Init the shaped pane.
     */
    private void initShapedPane() {
        // activate inverted colors and round corners
        shapedPane = new ShapedPane();
        shapedPane.setInvertColors();
        shapedPane.setRadius(radius);

        shapedPane.addLabel(label);
        setContentPane(shapedPane);

        if(isImportant) {
            shapedPane.setImportantTooltip();
        }

        pack();
    }

    public void set(Component parent, String txt) {
        component = parent;

        parentTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                click = e.getPoint();
            }
        });

        parentTitle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(isVisible()) {
                    display();
                }
            }
        });

        // set the text (note: can be HTML formatted)
        label.setText(txt);
        initShapedPane();
    }

    private void display() {
        int x = component.getLocationOnScreen().x + component.getWidth()/2;
        int y = component.getLocationOnScreen().y + component.getHeight()/2 - getHeight()/2;

        if(!center) {
            x += component.getWidth()/2 + VGAP;
        } else {
            x -= this.getWidth()/2;
        }

        // check if we have enough space left on the screen (right next to the component)
        if((x + getWidth()) >=  screenSize.getWidth()) {
            x = component.getLocationOnScreen().x - getWidth() - VGAP;
        }

        setLocation(x,y);
        setVisible(true);
        setAlwaysOnTop(true);
    }

    private void startTimer(int ms) {
        // shall the tooltip be faded out?
        if(ms > 0) {
            // start timer(s) to fade out the tooltip
            tmrA = new Timer(ms, this);
            tmrB = new Timer(eventTime, this);
            tmrA.start();
        }

        // start with a opacity 1.0
        opacity = 1.0f;
        setOpacity(opacity);
    }

    public void open() {
        display();
        startTimer(1000);
    }

    public void open(int ms) {
        display();
        startTimer(ms);
    }

    public void close() {
        setVisible(false);
    }

    public void setCentered() {
        center = true;
    }

    public void setImportant(boolean important) {
        isImportant = important;
        shapedPane.setImportantTooltip();
        label.setForeground(Config.getInstance().getTheme().getImportantTooltipForeground());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String eventSource = e.getSource().toString();

        if(eventSource.equals(tmrA.toString())) {
            tmrA.stop();
            tmrB.start();
        } else if(eventSource.equals(tmrB.toString())) {
            if(opacity > opacitySteps) {
                opacity -= opacitySteps;
                setOpacity(opacity);
            } else {
                tmrB.stop();
                setVisible(false);
            }
        }
    }
}
