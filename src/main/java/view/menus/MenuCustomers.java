package view.menus;

import view.EForm;

import javax.swing.*;
import java.awt.*;

public class MenuCustomers extends EForm {
    private JPanel contentPane;
    private JLabel labelTitle;
    private JLabel labelIntro;

    public MenuCustomers() {
        super();
        init();
    }

    /**
     * This will return with the content pane.
     * @return Content pane.
     */
    public JPanel getContentPane() {
        return contentPane;
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
