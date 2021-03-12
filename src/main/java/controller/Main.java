package controller;

import javax.swing.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import view.Login;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        createAndShowGUI();
    }

    private static void createAndShowGUI() {
        Login login = new Login();

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            logger.error(e);
        }

        SwingUtilities.invokeLater(() -> login.setVisible(true));
        logger.trace("System look and feel set");
    }
}
