package view.main.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;

public class PageHistory extends EForm {
    private static final PageHistory OBJ = new PageHistory();

    private JLabel labelTitle;
    private JLabel labelIntro;
    private JPanel menuPane;
    private JPanel contentPane;

    private PageHistory() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageHistory getInstance() {
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
