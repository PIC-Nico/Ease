package view.main.pages;

import model.Users;
import view.misc.EForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageOverview extends EForm {
    private final static PageOverview OBJ = new PageOverview();

    private JLabel labelTitle;
    private JLabel labelIntro;
    private JButton lightButton;
    private JButton darkButton;
    private JPanel menuPane;
    private JPanel contentPane;

    private PageOverview() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();

        printHello();
    }

    public static PageOverview getInstance() {
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

    /**
     * This will print a hello user message.
     */
    private void printHello() {
        String userFirstName = Users.getInstance().getActiveUser();
        userFirstName = userFirstName.substring(0, userFirstName.indexOf(" "));

        labelTitle.setText("Hallo " + userFirstName + ",");
    }
}
