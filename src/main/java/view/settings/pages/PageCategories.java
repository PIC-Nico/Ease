package view.settings.pages;

import view.misc.EForm;

import javax.swing.*;
import java.awt.*;

public class PageCategories extends EForm {
    private static final PageCategories OBJ = new PageCategories();

    private JPanel contentPane;
    private JPanel menuPane;
    private JLabel labelTitle;
    private JLabel labelIntro;

    private PageCategories() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();
    }

    public static PageCategories getInstance() {
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
