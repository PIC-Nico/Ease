package view.menus;

import model.Users;
import view.EForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuOverview extends EForm {
    private JLabel labelTitle;
    private JLabel labelIntro;
    private JPanel contentPane;
    private JButton lightButton;
    private JButton darkButton;

    public MenuOverview() {
        super();
        init();

        printHello();

        lightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setTheme("Light");
            }
        });
        darkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setTheme("Dark");
            }
        });
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

    /**
     * This will print a hello user message.
     */
    private void printHello() {
        labelTitle.setText("Hallo " + Users.getInstance().getActiveUser() + ",");
    }
}
