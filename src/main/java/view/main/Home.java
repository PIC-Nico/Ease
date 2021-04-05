package view.main;

import controller.Theme;
import view.misc.EForm;
import view.main.pages.*;
import view.settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home extends EForm {
    private JPanel contentPane;
    private JPanel sidebarPane;
    private JPanel menuPane;
    private JLabel labelMenuSettings;

    // menus
    public final Settings settings = Settings.getInstance();

    public Home() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        setSidebar(sidebarPane);
        exitOnClose = true;
        paintWindowBorder = true;

        windowTitle = "Ease - Verwaltung";
        windowIcon = "icons/misc/32/logo.png";
        titleIcon = "icons/misc/16/logo.png";
        addResizer = true;

        init();
    }

    /**
     * Please add all pages of your form to the pages list.
     * Important: The order of adding pages has to match the order of the sidebar menus!
     */
    public void addPages() {
        pages.add(PageOverview.getInstance());
        pages.add(PageCustomers.getInstance());
        pages.add(PageOffers.getInstance());
        pages.add(PageInvoices.getInstance());
        pages.add(PageHistory.getInstance());
        pages.add(PageStatistics.getInstance());
        pages.add(PageHelp.getInstance());
        pages.add(null);
        pages.add(PagePrint.getInstance());

        pages.forEach(x -> {
            if(x != null) {
                x.setParent(this);
            }
        });
    }

    /**
     * Will automatically be called once a theme change event occurred.
     * @param theme The new active color theme.
     */
    public void themeChangeUser(Theme theme) {
    }

    /**
     * Will automatically be called once a font change event occurred.
     * @param font The new active (main) font.
     */
    public void fontChangeUser(Font font) {
    }

    /**
     * Use this to set some menu item descriptions.
     */
    public void setMenusProperties() {
        labelMenuSettings.getAccessibleContext().setAccessibleDescription("Highlight");
    }

    /**
     * This will add mouse listener to all sidebar menu items.
     */
    public void addSidebarMenuListener() {
        labelMenuSettings.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                settings.setVisible(true);
            }
        });
    }

    /**
     * This will update the form size (in case the incomming form has a bigger menu pane).
     * @param x Form with new size.
     */
    public void updateSize (EForm x) {
        Dimension incommingSize = x.getPreferredSize();
        Dimension currentSize = menuPane.getPreferredSize();
        boolean change = false;

        // is this menu bigger?
        if(incommingSize.width > currentSize.width) {
            change = true;
            currentSize.width = incommingSize.width;
        }
        if(incommingSize.height > currentSize.height) {
            change = true;
            currentSize.height = incommingSize.height;
        }

        if(change) {
            EForm restoreMenu = activeMenu;

            // trmporarily open the menu
            openMenu(x);
            menuPane.setPreferredSize(currentSize);
            pack();

            // set the current size as minimum size
            setMinimumSize(getSize());

            // was there a menu to restore?
            if(restoreMenu != null) {
                openMenu(restoreMenu);
            }
        }
    }
}
