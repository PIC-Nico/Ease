package view.misc;

import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import static controller.Utils.getIconFromResource;
import static controller.Utils.getImageFromResource;

/**
 * Ease base form
 */
public class EForm extends JFrame implements PropertyChangeListener {
    protected Config config = Config.getInstance();

    // to resize the undecorated form
    private ComponentResizer resizer = new ComponentResizer();

    // Log4J2 logger object
    protected final Logger logger = LogManager.getLogger(Main.class);

    // can be used to deny adding a component resizer
    public boolean addResizer = false;

    // parent of this form (e.g. Home is the parent for PageOverview)
    private EForm parent;

    // list of all components within the frame
    private ArrayList<JLabel> allLabels;
    private ArrayList<JPanel> allPanels;
    private ArrayList<JTextField> allTextFields;
    private ArrayList<JCheckBox> allCheckBoxes;
    private ArrayList<JButton> allButtons;
    private ArrayList<JComboBox> allComboBoxes;
    private ArrayList<JSeparator> allSeparators;

    // list of all pages
    public ArrayList<EForm> pages;

    // window state control components
    private JLabel labelWindowTitle;
    private JLabel labelWindowMinimize;
    private JLabel labelWindowMaximize;
    private JLabel labelWindowClose;

    // window title and icons
    private JPanel paneTitle;
    public String windowTitle;
    public String windowIcon;
    public String titleIcon;

    // window size options
    private int width;
    private int height;
    public int minWidth;
    public int minHeight;
    public boolean resizeable = true;

    // menu components
    private JPanel menu;
    private JPanel sidebar;
    private JLabel activeSidebarMenu;

    // current active menu (necessary to restore before change to another menu)
    public EForm activeMenu;

    // list to hold all sidebar menus
    public ArrayList<JLabel> sidebarMenus;

    // window position/state
    private int x;
    private int y;
    private boolean isMaximized;

    // will be used to get the location of a mouse click
    private Point click;

    // can be used to show additional information
    public Tooltip tooltip;

    // window misc
    public boolean paintWindowBorder;
    public boolean exitOnClose;

    /**
     * The default constructor
     */
    public EForm() {
        initMembers();

        // set some window properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);

        // add property change listener to the config
        config.addPropertyChangeListener(this);

        // add trace info about constructor finished
        logger.trace("Super constructor finished (" + this.getClass().getName() + ")");
    }

    private void initMembers() {
        allLabels = new ArrayList<>();
        allPanels = new ArrayList<>();
        allTextFields = new ArrayList<>();
        allCheckBoxes = new ArrayList<>();
        allButtons = new ArrayList<>();
        allComboBoxes = new ArrayList<>();
        allSeparators = new ArrayList<>();

        pages = new ArrayList<>();
    }

    /**
     * Do some very basic initialization for the ease form.
     */
    protected void init() {
        initSidebar();
        collectAllComponents(getContentPane());
        tooltip = new Tooltip(paneTitle);
        addPages();
        applyComponentSettings();
        applyListener();
        applyThemeChange(Config.getInstance().getTheme());
        applyFontChange(Config.getInstance().getNormalFont());
        applyWindowSettings();

        // open the first page (if pages are available for this form)
        if(!pages.isEmpty()) {
            openMenu(pages.get(0));
        }

        // open the window centered
        setLocationRelativeTo(null);
    }

    public void setParent (EForm parent) {
        this.parent = parent;
        parent.updateSize(this);
    }

    protected void setMenu(JPanel menu) {
        this.menu = menu;
    }

    protected void setSidebar(JPanel sidebar) {
        this.sidebar = sidebar;
    }

    /**
     * This will handle the complete init of the sidebar.
     */
    private void initSidebar() {
        if(sidebar == null) {
            return;
        }

        sidebarMenus = new ArrayList<>();

        collectSidebarMenus(sidebar);
        activeSidebarMenu = sidebarMenus.get(0);
        setMenusProperties();
        styleSidebar(config.getTheme());
        addSidebarListener();
        addSidebarMenuListener();
    }

    /**
     * Use this to set some menu item descriptions.
     */
    public void setMenusProperties() {
    }

    /**
     * This will collect all components of the sidebar.
     * Note: Will be called recursive (once another container was found).
     * @param container Container to search through.
     */
    private void collectSidebarMenus(Container container) {
        // iterate over all components of the sidebar
        for (int i = 0; i < container.getComponentCount(); i++) {
            Component c = container.getComponent(i);

            // add if this is a label
            if (c instanceof JLabel) {
                sidebarMenus.add((JLabel) c);
            } else if(c instanceof JPanel) {
                collectSidebarMenus((Container) c);
            }
        }
    }

    /**
     * This will handle styling the sidebar.
     * @param theme Active theme to apply to the sidebar.
     */
    private void styleSidebar(Theme theme) {
        if(sidebar == null) {
            return;
        }

        sidebar.setBackground(theme.getSidebarBackground());
        sidebarMenus.forEach(x -> {
            x.setOpaque(true);

            Color bg = theme.getSidebarBackground();
            Border border = theme.getSidebarMenuBorder();

            if(x.equals(activeSidebarMenu)) {
                bg = theme.getSidebarActiveMenuBackground();
                border = theme.getSidebarActiveMenuBorder();
            }

            x.setBackground(bg);
            x.setBorder(border);
            x.setCursor(new Cursor(Cursor.HAND_CURSOR));
        });
    }

    /**
     * This will add basic listeners to the sidebar (e.g. mouse hover).
     */
    private void addSidebarListener() {
        sidebarMenus.forEach(x -> {
            x.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    activeSidebarMenu = (JLabel) e.getComponent();
                    styleSidebar(config.getTheme());

                    // check which menu was clicked and open its page
                    for(int i=0; i<sidebarMenus.size(); ++i) {
                        if(sidebarMenus.get(i).equals(activeSidebarMenu)) {
                            if(i < pages.size()) {
                                if(pages.get(i) != null) {
                                    openMenu(pages.get(i));
                                }
                            } else {
                                logger.warn("No page available for: " + activeSidebarMenu.getText());
                            }
                        }
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    handleSidebarMenuHover(e.getComponent());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Color fg = config.getTheme().getForeground();
                    Color bg = config.getTheme().getSidebarBackground();

                    if(e.getComponent().equals(activeSidebarMenu)) {
                        bg = config.getTheme().getSidebarActiveMenuBackground();
                    }

                    e.getComponent().setForeground(fg);
                    e.getComponent().setBackground(bg);
                }
            });
        });
    }

    /**
     * This will handle sidebar menu mouse hover events.
     */
    private void handleSidebarMenuHover(Component c) {
        String description = c.getAccessibleContext().getAccessibleDescription();

        Color fg = config.getTheme().getSidebarMenuHoverForeground();
        Color bg = config.getTheme().getSidebarMenuHoverBackground();

        if(description != null) {
            switch (description) {
                case "Dangerous": {
                    fg = config.getTheme().getSidebarDangerousMenuHoverForeground();
                    bg = config.getTheme().getSidebarDangerousMenuHoverBackground();
                    break;
                }
                case "Highlight": {
                    fg = config.getTheme().getSidebarHighlightMenuHoverForeground();
                    bg = config.getTheme().getSidebarHighlightMenuHoverBackground();
                    break;
                }
            }
        }

        if(c.equals(activeSidebarMenu)) {
            fg = config.getTheme().getSidebarActiveMenuHoverForeground();
            bg = config.getTheme().getSidebarActiveMenuHoverBackground();
        }

        c.setForeground(fg);
        c.setBackground(bg);
    }

    /**
     * This will add mouse listener to all sidebar menu items.
     */
    public void addSidebarMenuListener() {
    }

    /**
     * This needs to be overwritten in order to add pages to the list.
     */
    public void addPages() {
    }

    /**
     * This will open a menu inside the menuPane.
     * @param menu The menu to open (represented as JPanel with its components).
     */
    protected void openMenu(EForm menu) {
        if(activeMenu != null) {
            transfer(this.menu, activeMenu.getMenu());
        }

        // load the new menu
        transfer(menu.getMenu(), this.menu);

        // save the new as active menu
        activeMenu = menu;

        this.menu.updateUI();
    }

    /**
     * This needs to be called once on start to set some window settings.
     */
    private void applyWindowSettings() {
        // set the minimum window size
        if(minWidth > 0 && minHeight > 0) {
            setMinimumSize(new Dimension(minWidth, minHeight));
        } else {
            setMinimumSize(getPreferredSize());
        }

        if(resizeable && addResizer) {
            resizer.registerComponent(this);
            resizer.setMinimumSize(getMinimumSize());
            resizer.setDragInsets(new Insets(10,10,10,10));
        }

        // window state control labels needs to be opaque (to paint background themselves)
        if(labelWindowMinimize != null) {
            labelWindowMinimize.setOpaque(true);
        }
        if(labelWindowMaximize != null) {
            labelWindowMaximize.setOpaque(true);
        }
        if(labelWindowClose != null) {
            labelWindowClose.setOpaque(true);
        }

        if(windowTitle != null) {
            setTitle(windowTitle);

            if(labelWindowTitle != null) {
                // some space (left) to the window title
                labelWindowTitle.setBorder(new EmptyBorder(0, 4, 0, 0));
                labelWindowTitle.setText(windowTitle);

                if(titleIcon != null) {
                    labelWindowTitle.setIcon(getIconFromResource(titleIcon));
                    logger.trace("Set window title icon to \"" + titleIcon + "\" (" + getClass().getName() + ")");
                }
            }

            logger.trace("Set window title to \"" + windowTitle + "\" (" + getClass().getName() + ")");
        }

        if(windowIcon != null) {
            setIconImage(getImageFromResource(windowIcon));
            logger.trace("Set window icon to \"" + windowIcon + "\" (" + getClass().getName() + ")");
        }
    }

    /**
     * This needs to be called once the user requests a theme change.
     * @param theme The new color theme
     */
    private void applyThemeChange(Theme theme) {
        allPanels.forEach(x -> {
            x.setBackground(theme.getBackground());
        });

        allLabels.forEach(x -> {
            x.setForeground(theme.getForeground());
            x.setBackground(theme.getBackground());
        });

        allTextFields.forEach(x -> {
            x.setForeground(theme.getForeground());
            x.setCaretColor(theme.getForeground());
            x.setBackground(theme.getTextBackground());
        });

        allButtons.forEach(x -> {
            String name = x.getName();
            Color fg = theme.getForeground();
            Color bg = theme.getButtonBackground();

            if(name != null) {
                switch(name) {
                    case "Important": {
                        fg = theme.getImportantButtonForeground();
                        bg = theme.getImportantButtonBackground();
                        break;
                    }
                    case "Highlight": {
                        fg = theme.getHighlightButtonForeground();
                        bg = theme.getHighlightButtonBackground();
                        break;
                    }
                }
            }

            x.setBackground(bg);
            x.setForeground(fg);
        });

        allCheckBoxes.forEach(x -> {
            x.setForeground(theme.getForeground());
        });

        allComboBoxes.forEach(x -> {
            BasicComboPopup popup = (BasicComboPopup)(x.getAccessibleContext().getAccessibleChild(0));

            popup.getList().setBackground(theme.getComboboxBackground());
            popup.getList().setSelectionBackground(theme.getComboboxHoverBackground());
            popup.getList().setForeground(theme.getForeground());
            popup.getList().setSelectionForeground(theme.getForeground());
        });

        allSeparators.forEach(x -> {
            x.setBackground(theme.getBackground());
        });

        if(labelWindowMinimize != null) {
            updateWindowControlLabel(labelWindowMinimize);
        }

        if(labelWindowMaximize != null) {
            updateWindowControlLabel(labelWindowMaximize);
        }

        if(labelWindowClose != null) {
            updateWindowControlLabel(labelWindowClose);
        }

        updateWindowBorder();
        styleSidebar(theme);
        themeChangeUser(theme);
    }

    /**
     * This needs to be called once a font change event was received.
     * @param font The new active font.
     */
    private void applyFontChange(Font font) {
        allLabels.forEach(x -> {
            x.setFont(font);
        });

        allButtons.forEach(x -> {
            x.setFont(font);
        });

        allTextFields.forEach(x -> {
            x.setFont(font);
        });

        allCheckBoxes.forEach(x -> {
            x.setFont(font);
        });

        allComboBoxes.forEach(x-> {
            x.setFont(font);
        });

        fontChangeUser(font);

        if(parent != null) {
            parent.updateSize(this);
        } else {
            pack();
        }
    }

    /**
     * This can be overwritten by the form in order to do further changes once a theme change was applied.
     * @param theme The new active color theme.
     */
    public void themeChangeUser(Theme theme) {
    }

    /**
     * This can be overwritten by the form in order to do further changes once a font change was applied.
     * @param font The new active (main) font.
     */
    public void fontChangeUser(Font font) {}

    /**
     * This needs to be called once on start in order to set some necessary component settings.
     */
    private void applyComponentSettings() {
        allTextFields.forEach(x -> {
            EmptyBorder border = new EmptyBorder(4,4,3,4);
            x.setBorder(border);
        });

        allCheckBoxes.forEach(x -> {
            x.setOpaque(false);
            x.setMargin(new Insets(2,0,2,0));
            x.setCursor(new Cursor(Cursor.HAND_CURSOR));
            x.setBorder(new EmptyBorder(2,2,2,2));
            x.setFocusPainted(false);
        });

        allButtons.forEach(x -> {
            x.setBorderPainted(false);
            x.setFocusPainted(false);
            x.setBorder(new EmptyBorder(6,6,6,6));
        });

        allComboBoxes.forEach(x -> {
//            x.setRenderer(new ComboBoxRenderer());


        });

        allSeparators.forEach(x -> {
            Dimension d = new Dimension(-1,1);;
            if(x.getOrientation() == JSeparator.VERTICAL) {
                d = new Dimension(1,-1);
            }
            x.setMaximumSize(d);
            x.setMinimumSize(d);
        });
    }

    /**
     * This will collect all components within the frame.
     * @param container The container to scan for components.
     */
    private void collectAllComponents(Container container) {
        //  iterate over all components within this container
        for (Component c : container.getComponents()) {
            addToComponentList(c);
            checkForKeyComponent(c);

            // do a recursive search in case of container
            if (c instanceof Container) {
                collectAllComponents((Container) c);
            }
        }

        // add the container as well
        addToComponentList(container);
    }

    /**
     * Determine the type of the component and add it to its desired list.
     * @param c The component to add.
     */
    private void addToComponentList(Component c) {
        if(c instanceof JLabel) {
            allLabels.add((JLabel) c);
        } else if(c instanceof JPanel) {
            allPanels.add((JPanel) c);
        } else if(c instanceof JTextField) {
            allTextFields.add((JTextField) c);
        } else if(c instanceof JCheckBox) {
            allCheckBoxes.add((JCheckBox) c);
        } else if(c instanceof JButton) {
            allButtons.add((JButton) c);
        } else if(c instanceof JComboBox) {
            allComboBoxes.add((JComboBox) c);
        } else if(c instanceof JSeparator) {
            allSeparators.add((JSeparator) c);
        }
    }

    /**
     * This will check if a component is one if the window key component (e.g. the title label).
     * If a key component was found ot will be set to the private class member.
     * @param c The component to check.
     */
    private void checkForKeyComponent(Component c) {
        String name = c.getName();

        if(name == null) {
            return;
        }

        switch(name) {
            case "WindowMinimize": {
                if(c instanceof JLabel) {
                    labelWindowMinimize = (JLabel) c;
                    logger.trace("Minimize window label found (" + getClass().getName() + ")");
                }
                break;
            }
            case "WindowMaximize": {
                if(c instanceof JLabel) {
                    labelWindowMaximize = (JLabel) c;
                    logger.trace("Maximize/restore window label found (" + getClass().getName() + ")");
                }
                break;
            }
            case "WindowClose": {
                if(c instanceof JLabel) {
                    labelWindowClose = (JLabel) c;
                    logger.trace("Close window label found (" + getClass().getName() + ")");
                }
                break;
            }
            case "WindowTitle": {
                if (c instanceof JLabel) {
                    labelWindowTitle = (JLabel) c;
                    logger.trace("Window title label found (" + getClass().getName() + ")");
                }
                break;
            }
            case "TitleBar": {
                if(c instanceof JPanel) {
                    paneTitle = (JPanel) c;
                    logger.trace("Title bar panel found (" + getClass().getName() + ")");
                }
                break;
            }
        }
    }

    /**
     * This will apply some listener (for window interaction e.g. close).
     */
    private void applyListener() {
        if(paneTitle != null) {
            /*
              This will maximize the window or restore its previous size (if already maximized).
              It will be triggered if the user clicked twice into the title bar of the window.
             */
            paneTitle.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(e.getClickCount() == 2 && resizeable) {
                        maximizeNormalizeWindow();
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    click = e.getPoint();
                }
            });

            paneTitle.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    // determine how much the mouse moved since the initial click
                    int moveX = e.getX() - click.x;
                    int moveY = e.getY() - click.y;

                    // move window to this position
                    setLocation(getLocation().x + moveX,getLocation().y + moveY);

                    isMaximized = false;
                    updateWindowControlLabel(labelWindowMaximize);
                }
            });

            logger.trace("Add title listener (" + getClass().getName() + ")");
        }

        if(labelWindowMinimize != null) {
            labelWindowMinimize.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    setState(Frame.ICONIFIED);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    labelWindowMinimize.setBackground(config.getTheme().getWindowControlHover());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateWindowControlLabel(labelWindowMinimize);
                }
            });

            logger.trace("Add minimize listener (" + getClass().getName() + ")");
        }

        if(labelWindowMaximize != null) {
            labelWindowMaximize.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    maximizeNormalizeWindow();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    labelWindowMaximize.setBackground(config.getTheme().getWindowControlHover());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateWindowControlLabel(labelWindowMaximize);
                }
            });

            logger.trace("Add maximize/restore listener (" + getClass().getName() + ")");
        }

        if(labelWindowClose != null) {
            labelWindowClose.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(exitOnClose) {
                        System.exit(0);
                    } else {
                        dispose();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    Icon icon = getIconFromResource("icons/window/close_white.png");
                    labelWindowClose.setIcon(icon);
                    labelWindowClose.setBackground(config.getTheme().getWindowCloseHover());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    updateWindowControlLabel(labelWindowClose);
                }
            });

            logger.trace("Add close listener (" + getClass().getName() + ")");
        }

        allTextFields.forEach(x -> {
            x.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    Container container = ((JTextField) e.getSource()).getParent();

                    if (container != null) {
                        if (container instanceof JPanel) {
                            JPanel panel = (JPanel) container;
                            Color color = config.getTheme().getActiveTextField();
                            MatteBorder border = new MatteBorder(0, 0, 1, 0, color);
                            panel.setBorder(border);
                        }
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                    Container container = ((JTextField) e.getSource()).getParent();

                    if (container != null) {
                        if (container instanceof JPanel) {
                            JPanel panel = (JPanel) container;
                            EmptyBorder border = new EmptyBorder(0, 0, 1, 0);
                            panel.setBorder(border);
                        }
                    }
                }
            });
        });

        allButtons.forEach(x -> {
            x.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {
                    e.getComponent().setBackground(Config.getInstance().getTheme().getButtonHover());

                    String name = e.getComponent().getName();
                    Color color = Config.getInstance().getTheme().getButtonHover();

                    if(name != null) {
                        switch (name) {
                            case "Important": {
                                color = Config.getInstance().getTheme().getImportantButtonHover();
                                break;
                            }
                            case "Highlight": {
                                color = Config.getInstance().getTheme().getHighlightButtonHover();
                            }
                        }
                    }

                    e.getComponent().setBackground(color);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    String name = e.getComponent().getName();
                    Color color = Config.getInstance().getTheme().getButtonBackground();

                    if(name != null) {
                        switch (name) {
                            case "Important": {
                                color = Config.getInstance().getTheme().getImportantButtonBackground();
                                break;
                            }
                            case "Highlight": {
                                color = Config.getInstance().getTheme().getHighlightButtonBackground();
                                break;
                            }
                        }
                    }

                    e.getComponent().setBackground(color);
                }
            });
        });

        if(paintWindowBorder) {
            addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                    super.windowGainedFocus(e);
                    updateWindowBorder();
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    super.windowLostFocus(e);
                    updateWindowBorder();
                }
            });
        }
    }

    /**
     * This will update the window border (depends on whether the window has focus or not).
     */
    private void updateWindowBorder() {
        Border border = config.getTheme().getInactiveWindowBorder();

        if(!paintWindowBorder) {
            return;
        }

        if(isFocused()) {
            border = config.getTheme().getWindowBorder();
        }

        ((JPanel)(getContentPane())).setBorder(border);
    }

    /**
     * This will update the background and the icon of the window control labels.
     */
    private void updateWindowControlLabel(JLabel label) {
        String name = "icons/window/";
        Icon icon;

        // sanity checks
        if(label == null || label.getName() == null) {
            return;
        }

        switch(label.getName()) {
            case "WindowMinimize": {
                switch(config.getTheme().getName()) {
                    case "Hell": {
                        name += "minimize_black.png";
                        break;
                    }
                    case "Dunkel": {
                        name += "minimize_white.png";
                        break;
                    }
                    default: {
                        return;
                    }
                }
                break;
            }
            case "WindowMaximize": {
                switch(config.getTheme().getName()) {
                    case "Hell": {
                        if(isMaximized) {
                            name += "restore_black.png";
                        } else{
                            name += "maximize_black.png";
                        }
                        break;
                    }
                    case "Dunkel": {
                        if(isMaximized) {
                            name += "restore_white.png";
                        } else{
                            name += "maximize_white.png";
                        }
                        break;
                    }
                    default: {
                        return;
                    }
                }
                break;
            }
            case "WindowClose": {
                switch(config.getTheme().getName()) {
                    case "Hell": {
                        name += "close_black.png";
                        break;
                    }
                    case "Dunkel": {
                        name += "close_white.png";
                        break;
                    }
                    default: {
                        return;
                    }
                }
                break;
            }
            default: {
                return;
            }
        }

        icon = getIconFromResource(name);
        label.setIcon(icon);
        label.setBackground(config.getTheme().getBackground());
    }

    /**
     * This will maximize or restore the window depending on the current state.
     */
    private void maximizeNormalizeWindow() {
        if(isMaximized) {
            restore();
        } else {
            maximize();
        }

        updateWindowControlLabel(labelWindowMaximize);
    }

    /**
     * This will maximize the window and save the latest window size and position (to restore later on).
     */
    private void maximize() {
        isMaximized = true;

        // save recent window size
        width = getSize().width;
        height = getSize().height;

        // save recent window position
        x = getLocationOnScreen().x;
        y = getLocationOnScreen().y;

        // maximize the window
        Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        setSize(rec.width, rec.height);
        setLocationRelativeTo(null);
    }

    /**
     * This will restore the window size and position.
     */
    private void restore() {
        isMaximized = false;
        setSize(width, height);
        setLocation(x, y);
    }

    /**
     * This will transfer all components from one menu to another.
     * @param src Source
     * @param dst Destination
     */
    public void transfer(JPanel src, JPanel dst) {
        Component[] list = src.getComponents();
        GridLayoutManager layout = (GridLayoutManager) src.getLayout();

        if(list.length == 0) {
            return;
        }

        dst.setLayout(layout);

        for (int i=0; i < list.length; ++i) {
            Component c = list[i];
            dst.add(c, layout.getConstraintsForComponent(c));
        }
    }

    /**
     * This will return with the menu pane.
     * @return Content pane.
     */
    public JPanel getMenu() {
        return menu;
    }

    /**
     * This should be overwritten by the parent form in order to update its size
     * once children forms has changed their size.
     * @param x Form with new size.
     */
    public void updateSize(EForm x) {
    }

    /**
     * Will be called on a property change event.
     * @param evt The fired event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String info = "Property change event: ";

        info += evt.getPropertyName() + " -> ";
        info += "Old: " + evt.getOldValue() + " / ";
        info += "New: " + evt.getNewValue() + " ";
        info += "(" + getClass().getName() + ")";

        logger.trace(info);

        switch (evt.getPropertyName()) {
            case "Theme": {
                applyThemeChange((Theme)evt.getSource());
                break;
            }
            case "Font": {
                applyFontChange((Font)evt.getSource());
                break;
            }
            default: {
                logger.warn("Unknown PropertyChange event: " + evt.getPropertyName());
            }
        }
    }
}
