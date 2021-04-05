package view.main.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;

public class PageCustomers extends EForm {
    private static final PageCustomers OBJ = new PageCustomers();

    private JLabel labelTitle;
    private JLabel labelIntro;
    private JPanel menuPane;
    private JPanel contentPane;
    private JButton buttonNewCustomer;

    private PageCustomers() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageCustomers getInstance() {
        return OBJ;
    }

    /**
     * This will return with the menu pane.
     * @return Content pane.
     */
    public JPanel getMenu() {
        return menuPane;
    }

    /**
     * Will automatically be called once a font change event occurred.
     * @param font The new active (main) font.
     */
    public void fontChangeUser(Font font) {
        labelTitle.setFont(config.getExtraLargeFont());
        labelIntro.setFont(config.getNormalFont());
    }
}
