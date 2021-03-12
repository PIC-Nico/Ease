package view;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.Config;
import controller.Theme;
import model.Users;
import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.*;
import java.util.Locale;

public class Login extends EForm {
    private JPanel contentPane;
    private JTextField textFieldName;
    private JPasswordField passwordField;
    private JButton buttonLogin;
    private JButton buttonNewUser;
    private JCheckBox showPasswordEnable;
    private JButton buttonForgotPassword;
    private JCheckBox checkBoxRemember;

    private boolean isNameExample = true;
    private boolean isPasswordExample = true;

    // singleton pattern object
    private final Users users = Users.getInstance();

    public Login() {
        super();
        shapedPane.add(contentPane);

        windowTitle = "Login";
        windowIcon = "icons/window/logo_32.png";
        titleIcon = "icons/window/logo_16.png";
        resizeable = false;
        minWidth = contentPane.getPreferredSize().width + 100;
        minHeight = contentPane.getPreferredSize().height;

        init();
        addListener();
        fillUser();
        showPassword(false);

        logger.trace("Constructor finished (" + this.getClass().getName() + ")");
    }

    /**
     * This will fill the user in case the "remember" checkbox was activated last time.
     */
    private void fillUser() {
        String user = users.getRemember();

        if (user != null) {
            isNameExample = false;
            textFieldName.setText(user);
            textFieldName.setForeground(Config.getInstance().getTheme().getForeground());
            checkBoxRemember.setSelected(true);
            passwordField.requestFocus();
        }
    }

    /**
     * Additional user changes once a theme change event occurred.
     *
     * @param theme The new active color theme.
     */
    public void themeChangeUser(Theme theme) {
        if (isNameExample) {
            textFieldName.setForeground(theme.getLightForeground());
        } else {
            textFieldName.setForeground(theme.getForeground());
        }

        if (isPasswordExample) {
            passwordField.setForeground(theme.getLightForeground());
        } else {
            passwordField.setForeground(theme.getForeground());
        }
    }

    /**
     * Additional user changes once a font change event occurred.
     *
     * @param font The new active (main) font.
     */
    public void fontChangeUser(Font font) {
        // get the small font
        Font smallFont = config.getSmallFont();

        checkBoxRemember.setFont(smallFont);
        showPasswordEnable.setFont(smallFont);
    }

    /**
     * Add some listeners..
     */
    private void addListener() {
        textFieldName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isNameExample) {
                    textFieldName.setText("");
                    textFieldName.setForeground(Config.getInstance().getTheme().getForeground());
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldName.getText().isEmpty()) {
                    isNameExample = true;
                    textFieldName.setText("Nutzername");
                    textFieldName.setForeground(Config.getInstance().getTheme().getLightForeground());
                } else {
                    isNameExample = false;
                }
            }
        });

        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (isPasswordExample) {
                    passwordField.setText("");
                    passwordField.setForeground(Config.getInstance().getTheme().getForeground());
                    showPassword(false);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                char pwd[] = passwordField.getPassword();
                if (pwd.length == 0) {
                    isPasswordExample = true;
                    passwordField.setText("Passwort");
                    passwordField.setForeground(Config.getInstance().getTheme().getLightForeground());
                    showPassword(true);
                } else {
                    isPasswordExample = false;
                    showPassword(false);
                }
            }
        });

        passwordField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        showPasswordEnable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassword(showPasswordEnable.isSelected());
            }
        });

        buttonNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new CreateUser().setVisible(true);
            }
        });

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
    }

    /**
     * Call this to login with user name and password.
     */
    private void login() {
        String user = textFieldName.getText();
        String password = new String(passwordField.getPassword());
        String s = users.getSalt(user);
        String h = users.getHash(user);

        if (s == null || h == null) {
            showLoginFailTooltip();
            return;
        }

        // check if the password input is correct
        if (!BCrypt.hashpw(password, s).equals(h)) {
            showLoginFailTooltip();
            return;
        }

        // shall the user name be remembered (for the next login)
        if (checkBoxRemember.isSelected()) {
            users.setRemember(user);
        } else {
            users.setRemember(null);
        }

        users.setActiveUser(user);

        dispose();
        new Home().setVisible(true);
    }

    /**
     * This will display a login fail info tooltip.
     */
    private void showLoginFailTooltip() {
        tooltip.set(buttonLogin, "Benuter und/oder Passwort falsch");
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
        } else if (!showPasswordEnable.isSelected()) {
            passwordField.setEchoChar('•');
        }
    }

}
