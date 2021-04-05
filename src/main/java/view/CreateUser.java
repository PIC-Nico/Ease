package view;

import model.Users;
import org.mindrot.jbcrypt.BCrypt;
import view.misc.EForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class CreateUser extends EForm {
    private JPanel contentPane;
    private JTextField textFieldUserName;
    private JButton buttonOkay;
    private JPasswordField passwordField;
    private JPasswordField passwordFieldRepeat;
    private JCheckBox checkBoxShowPassword;
    private JButton buttonCancel;

    public CreateUser() {
        super();
        setContentPane(contentPane);

        windowTitle = "Neuer Benutzer";
        windowIcon = "icons/misc/32/logo.png";
        titleIcon = "icons/misc/16/logo.png";
        resizeable = false;

        init();
        addListener();

        logger.trace("Constructor finished (" + this.getClass().getName() + ")");
    }

    public void fontChangeUser(Font font) {
        Font smallFont = config.getSmallFont();
        checkBoxShowPassword.setFont(smallFont);
    }

    private void addListener() {
        checkBoxShowPassword.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassword(checkBoxShowPassword.isSelected());
            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!equalPasswordCheck()) {
                    showPasswordHint();
                } else {
                    tooltip.close();
                }
            }
        });

        passwordFieldRepeat.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!equalPasswordCheck()) {
                    showPasswordHint();
                } else {
                    tooltip.close();
                }
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLogin();
            }
        });

        buttonOkay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewUser(textFieldUserName.getText());
            }
        });
    }

    /**
     * This will create a new user (in case it does not already exist).
     *
     * @param user Name of the new user.
     */
    private void createNewUser(String user) {
        if (user.isEmpty()) {
            tooltip.set(textFieldUserName, "Erforderlich");
            tooltip.open();
            return;
        }

        Users users = Users.getInstance();

        // check if this user (name) is already in use
        if (users.exists(user)) {
            tooltip.set(textFieldUserName, "Bereits vergeben");
            tooltip.open();
            return;
        }

        // create a salt for the new user
        String salt = BCrypt.gensalt();

        // create the password hash
        String hash = BCrypt.hashpw(new String(passwordField.getPassword()), salt);

        // create the new user
        if (!users.create(user, salt, hash)) {
            logger.error("Creating a new user failed");
            return;
        }

        // return to the login
        logger.info("New user created");
        openLogin();
    }

    /**
     * This will close the create new user form and go (back) to the login form.
     */
    private void openLogin() {
        dispose();
        new Login().setVisible(true);
    }

    /**
     * This will check whether the password and the repeated password input are identical or not.
     *
     * @return True if identical otherwise false.
     */
    private boolean equalPasswordCheck() {
        char[] a = passwordField.getPassword();
        char[] b = passwordFieldRepeat.getPassword();

        if (a.length == 0 || b.length == 0) {
            return true;
        }

        if (Arrays.equals(a, b)) {
            return true;
        } else {
            return false;
        }
    }

    private void showPasswordHint() {
        tooltip.set(passwordFieldRepeat, "Passwörter sind nicht identisch");
        tooltip.setImportant(true);
        tooltip.open();
    }

    /**
     * This will show or hide the password input.
     * Note: Hiding the password will only work if the checkbox (showPassword) is not selected.
     *
     * @param show True to show and false to hide the password input.
     */
    private void showPassword(boolean show) {
        if (show) {
            passwordField.setEchoChar((char) 0);
            passwordFieldRepeat.setEchoChar((char) 0);
        } else if (!checkBoxShowPassword.isSelected()) {
            passwordField.setEchoChar('•');
            passwordFieldRepeat.setEchoChar('•');
        }
    }

}
