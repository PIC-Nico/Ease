package view.settings.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;

public class PageData extends EForm {
    private static final PageData OBJ = new PageData();

    private JPanel contentPane;
    private JPanel menuPane;
    private JLabel labelTitle;
    private JLabel labelIntro;

    private PageData() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageData getInstance() {
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
