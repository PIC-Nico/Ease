package view.main.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;

public class PageStatistics extends EForm {
    private static final PageStatistics OBJ = new PageStatistics();

    private JLabel labelTitle;
    private JLabel labelIntro;
    private JPanel menuPane;
    private JPanel contentPane;

    private PageStatistics() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageStatistics getInstance() {
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
