package view.settings.pages;

import controller.Themes;
import view.main.pages.PageOffers;
import view.misc.EForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PageAppearance extends EForm {
    private static final PageAppearance OBJ = new PageAppearance();
    private JPanel contentPane;
    private JPanel menuPane;
    private JLabel labelTitle;
    private JComboBox comboBoxTheme;
    private JLabel labelIntro;
    private JComboBox comboBoxFontSize;

    private PageAppearance() {
        super();
        setContentPane(contentPane);
        setMenu(menuPane);
        init();

        loadThemes();
        loadFontSizes();
        addListener();
    }

    public static PageAppearance getInstance() {
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

    private void addListener() {
        comboBoxTheme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setTheme(comboBoxTheme.getSelectedItem().toString());
            }
        });

        comboBoxFontSize.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                config.setFontSize(comboBoxFontSize.getSelectedItem().toString());
            }
        });
    }

    /**
     * This will get the available color themes and add it to the combobox.
     */
    private void loadThemes() {
        Themes.getInstance().getThemes().forEach(x -> {
            comboBoxTheme.addItem(x.getName());
        });

        // select the current active theme
        comboBoxTheme.setSelectedItem(config.getTheme().getName());
    }

    /**
     * Add available font sizes and select the current activated one.
     */
    private void loadFontSizes() {
        config.getFontSizes().forEach(x -> {
            comboBoxFontSize.addItem(x);
        });

        comboBoxFontSize.setSelectedItem(config.getFontSize());
    }
}
