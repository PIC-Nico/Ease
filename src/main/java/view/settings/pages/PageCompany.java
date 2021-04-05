package view.settings.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageCompany extends EForm {
    private static final PageCompany OBJ = new PageCompany();

    private JPanel contentPane;
    private JPanel menuPane;
    private JLabel labelTitle;
    private JLabel labelIntro;

    private PageCompany() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageCompany getInstance() {
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
