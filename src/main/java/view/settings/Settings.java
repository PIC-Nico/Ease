package view.settings;

import view.main.pages.*;
import view.misc.EForm;
import view.settings.pages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Settings extends EForm {
    private static final Settings OBJ = new Settings();

    private JPanel sidebarPane;
    private JPanel menuPane;
    private JPanel contentPane;
    private JLabel labelMenuAppearence;
    private JLabel labelMenuCompany;
    private JLabel labelMenuSafeMode;
    private JLabel labelMenuData;
    private JLabel labelMenuCategories;
    private JLabel labelMenuUser;

    private Settings() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        setSidebar(sidebarPane);
        paintWindowBorder = true;

        windowTitle = "Ease - Einstellungen";
        windowIcon = "icons/misc/32/settings.png";
        titleIcon = "icons/misc/16/settings.png";
        addResizer = true;

        init();
    }

    public static Settings getInstance() {
        return OBJ;
    }

    /**
     * Please add all pages of your form to the pages list.
     * Important: The order of adding pages has to match the order of the sidebar menus!
     */
    public void addPages() {
        pages.add(PageAppearance.getInstance());
        pages.add(PageSafeMode.getInstance());
        pages.add(PageData.getInstance());
        pages.add(PageCategories.getInstance());
        pages.add(PageUser.getInstance());
        pages.add(PageCompany.getInstance());

        pages.forEach(x -> {
            if(x != null) {
                x.setParent(this);
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
            // save the active menu (reopen later)
            EForm restoreMenu = activeMenu;

            // open the menu (temporarily)
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
