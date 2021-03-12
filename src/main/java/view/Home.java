package view;

import com.intellij.uiDesigner.core.GridLayoutManager;
import controller.Theme;
import model.Customers;
import model.Invoices;
import model.Offers;
import view.menus.MenuCustomers;
import view.menus.MenuOverview;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Home extends EForm {
    private JPanel contentPane;
    private JPanel sidebarPane;
    private JPanel menuContentPane;
    private JLabel labelMenuOverview;
    private JLabel labelMenuSettings;
    private JLabel labelMenuCustomers;
    private JLabel labelMenuOffers;
    private JLabel labelMenuInvoices;
    private JLabel labelMenuStatistics;
    private JLabel labelMenuHistory;
    private JLabel labelMenuPrint;
    private JLabel labelMenuHelp;

    // list to hold all sidebar menus
    private ArrayList<JLabel> sidebarMenus;

    // current active menu (necessary to restore before change to another menu)
    private JPanel activeMenu;

    // menus
    private final MenuOverview menuOverview = new MenuOverview();
    private final MenuCustomers menuCustomers = new MenuCustomers();

    public Home() {
        super();

        setContentPane(contentPane);

        windowTitle = "Ease - Verwaltung";
        windowIcon = "icons/window/logo_32.png";
        titleIcon = "icons/window/logo_16.png";
        adddResizer = true;
        minHeight = 600;
        minWidth = 800;

        initSidebar();
        init();

        // show the overview on start
        openMenu(menuOverview.getContentPane());

        Customers customers = Customers.getInstance();
        Offers offers = Offers.getInstance();
        Invoices invoices = Invoices.getInstance();
    }

    /**
     * Will automatically be called once a theme change event occurred.
     * @param theme The new active color theme.
     */
    public void themeChangeUser(Theme theme) {
        styleSidebar(theme);
    }

    /**
     * Will automatically be called once a font change event occurred.
     * @param font The new active (main) font.
     */
    public void fontChangeUser(Font font) {
    }

    /**
     * This will handle the complete init of the sidebar.
     */
    private void initSidebar() {
        sidebarMenus = new ArrayList<>();

        collectSidebarMenus(sidebarPane);
        setMenusProperties();
        styleSidebar(config.getTheme());
        addSidebarListener();
        addSidebarMenuListener();
    }

    /**
     * Use this to set some menu item descriptions.
     */
    private void setMenusProperties() {
        labelMenuSettings.getAccessibleContext().setAccessibleDescription("Highlight");
    }

    /**
     * Thiswill collect all components of the sidebar.
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
        sidebarPane.setBackground(theme.getSidebarBackground());
        sidebarMenus.forEach(x -> {
            x.setOpaque(true);
            x.setBorder(theme.getSidebarMenuBorder());
            x.setBackground(theme.getSidebarBackground());
            x.setCursor(new Cursor(Cursor.HAND_CURSOR));
        });
    }

    /**
     * This will add basic listeners to the sidebar (e.g. mouse hover).
     */
    private void addSidebarListener() {
        sidebarMenus.forEach(x -> {
            x.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {}

                @Override
                public void mousePressed(MouseEvent e) {}

                @Override
                public void mouseReleased(MouseEvent e) {}

                @Override
                public void mouseEntered(MouseEvent e) {
                    handleSidebarMenuHover(e.getComponent());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    e.getComponent().setForeground(config.getTheme().getForeground());
                    e.getComponent().setBackground(config.getTheme().getSidebarBackground());
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

        c.setForeground(fg);
        c.setBackground(bg);
    }

    /**
     * This will add mouse listener to all sidebar menu items.
     */
    private void addSidebarMenuListener() {
        labelMenuOverview.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMenu(menuOverview.getContentPane());
            }
        });

        labelMenuCustomers.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openMenu(menuCustomers.getContentPane());
            }
        });
    }

    /**
     * This will open a menu inside the menuContentPane.
     * @param menu The menu to open (represented as JPanel with its components).
     */
    private void openMenu(JPanel menu) {
        // transfer back to the origin menu
        if(activeMenu != null) {
            transferMenu(menuContentPane, activeMenu);
        }

        // save the new as active menu
        activeMenu = menu;

        // transfer from the new menu
        transferMenu(activeMenu, menuContentPane);

        // update the UI
        menuContentPane.updateUI();
    }

    /**
     * This will transfer all components from one menu to another.
     * @param src Source
     * @param dst Destination
     */
    private void transferMenu(JPanel src, JPanel dst) {
        Component[] list = src.getComponents();
        GridLayoutManager layout = (GridLayoutManager) src.getLayout();

        dst.setLayout(layout);

        for (int i=0; i < list.length; ++i) {
            Component c = list[i];
            dst.add(c, layout.getConstraintsForComponent(c));
        }
    }
}
