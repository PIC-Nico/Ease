package controller;

import model.Users;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class Config {
    // the singleton pattern object
    private static final Config OBJ = new Config();

    // singleton pattern object
    private final Users users = Users.getInstance();

    // Log4J2 logger object
    private final Logger logger = LogManager.getLogger(Config.class);

    // property change support (to be observable)
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    // some basics
    private final String fileSeparator = System.getProperty("file.separator");

    // preferences (user based)
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    // the root path to all app files/databases
    private final String ROOT_PATH = "Root:Path";

    private final String APPEARANCE_THEME = "Appearance:Theme";
    private final String APPEARANCE_FONT_FAMILY = "Appearance:Font:Family";

    private final String APPEARANCE_FONT_SIZE_XS = "Appearance:Font:Size:ExtraSmall";
    private final String APPEARANCE_FONT_SIZE_S  = "Appearance:Font:Size:Small";
    private final String APPEARANCE_FONT_SIZE_M  = "Appearance:Font:Size:Medium";
    private final String APPEARANCE_FONT_SIZE_L  = "Appearance:Font:Size:Large";
    private final String APPEARANCE_FONT_SIZE_XL = "Appearance:Font:Size:ExtraLarge";
    private final String APPEARANCE_FONT_SIZE_ACTIVE = "Appearance:Font:Size:Active";

    // default values
    private final String DEFAULT_ROOT_PATH = System.getProperty("user.home") + "\\AppData\\Local\\Ease\\";
    private final String DEFAULT_THEME = "Hell";
    private final String DEFAULT_FONT_FAMILY = "Century Gothic";
    //private final String FONT_FAMILY = "Segoe UI Light";
    private final String DEFAULT_FONT_SIZE = "Normal";

    private final int FONT_SIZE_XS = 10;
    private final int FONT_SIZE_S  = 12;
    private final int FONT_SIZE_M  = 14;
    private final int FONT_SIZE_L  = 18;
    private final int FONT_SIZE_XL = 22;

    // list of available font sizes
    private final ArrayList<String> fontSizes = new ArrayList<String>() {{
        add("Klein");
        add("Normal");
        add("Groß");
    }};
    private String activeFontSize;
    private int fontSizeOffset = 0;

    // user configurations
    private Theme theme;
    private Font font;
    private int fontSizeXS;
    private int fontSizeS;
    private int fontSizeM;
    private int fontSizeL;
    private int fontSizeXL;
    private String rootPath;

    /**
     * Constructor
     */
    private Config() {
        loadPreferences();
    }

    /**
     * Adds a new property change listener.
     * @param listener The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener.
     * @param listener The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * This will load the user preferences
     */
    private void loadPreferences() {
        // get the set app root path (take care for the file separator)
        rootPath = prefs.get(ROOT_PATH, DEFAULT_ROOT_PATH);
        rootPath = rootPath.replace("\\", fileSeparator);

        // load the user set color theme
        theme = Themes.getInstance().getTheme(prefs.get(APPEARANCE_THEME, DEFAULT_THEME));

        // load the user font
        String fontFamily = prefs.get(APPEARANCE_FONT_FAMILY, DEFAULT_FONT_FAMILY);

        fontSizeXS = prefs.getInt(APPEARANCE_FONT_SIZE_XS, FONT_SIZE_XS);
        fontSizeS  = prefs.getInt(APPEARANCE_FONT_SIZE_S, FONT_SIZE_S);
        fontSizeM  = prefs.getInt(APPEARANCE_FONT_SIZE_M, FONT_SIZE_M);
        fontSizeL  = prefs.getInt(APPEARANCE_FONT_SIZE_L, FONT_SIZE_L);
        fontSizeXL = prefs.getInt(APPEARANCE_FONT_SIZE_XL, FONT_SIZE_XL);

        activeFontSize = prefs.get(APPEARANCE_FONT_SIZE_ACTIVE, DEFAULT_FONT_SIZE);
        updateFontSizeOffset();

        // init the base/normal font
        font = new Font(fontFamily, Font.PLAIN, FONT_SIZE_M + fontSizeOffset);
    }

    /**
     * This will update the font size offset accordingly to the current active font size setting.
     */
    private void updateFontSizeOffset() {
        switch(activeFontSize) {
            case "Klein": {
                fontSizeOffset = -2;
                break;
            }
            case "Groß": {
                fontSizeOffset = 2;
                break;
            }
            default: {
                fontSizeOffset = 0;
            }
        }
    }

    /**
     * This will return with a extra small font that is derived from the normal system font but with a smaller font size.
     * @return The extrasmall font
     */
    public Font getExtraSmallFont() {
        return new Font(font.getName(), font.getStyle(), fontSizeXS + fontSizeOffset);
    }

    /**
     * This will return with a small font that is derived from the normal system font but with a smaller font size.
     * @return The small font
     */
    public Font getSmallFont() {
        return new Font(font.getName(), font.getStyle(), fontSizeS + fontSizeOffset);
    }

    /**
     * Get the main system font.
     * @return The main system font.
     */
    public Font getNormalFont() {
        return new Font(font.getName(), font.getStyle(), fontSizeM + fontSizeOffset);
    }

    /**
     * This will return with a large font that is derived from the normal system font but with a larger font size.
     * @return The large font
     */
    public Font getLargeFont() {
        return new Font(font.getName(), font.getStyle(), fontSizeL+ fontSizeOffset);
    }

    /**
     * This will return with an extra large font that is derived from the normal system font but with a larger font size.
     * @return The extra large font
     */
    public Font getExtraLargeFont() {
        return new Font(font.getName(), font.getStyle(), fontSizeXL + fontSizeOffset);
    }

    public Font getBoldFont() {
        return new Font(font.getName(), Font.BOLD, fontSizeM + fontSizeOffset);
    }

    /**
     * This will change the system font family.
     * @param family The new font family.
     */
    private void setFontFamily(String family) {
        String old = font.getFamily();

        font = new Font(family, font.getStyle(), font.getSize() + fontSizeOffset);

        if(font != null) {
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    font,
                    "Font",
                    old,
                    family
            );

            // notify about the change
            propertyChangeSupport.firePropertyChange(evt);
        }
    }

    /**
     * Get the singleton object.
     * @return Configuration object
     */
    public static Config getInstance() {
        return OBJ;
    }

    /**
     * Set a new active theme by name and notify all listener.
     * @param name Name of the requested theme.
     */
    public void setTheme(String name) {
        Theme newTheme = Themes.getInstance().getTheme(name);

        if(theme != null) {
            PropertyChangeEvent evt = new PropertyChangeEvent(
                    newTheme,
                    "Theme",
                    theme.getName(),
                    newTheme.getName()
            );

            // assign the new color theme
            theme = newTheme;

            // notify about the change
            propertyChangeSupport.firePropertyChange(evt);

            // save as new default
            prefs.put(APPEARANCE_THEME, theme.getName());
        }
    }

    public void setFontSize(String fontSize) {
        String oldFont = activeFontSize;
        String newFont = fontSize;

        activeFontSize = fontSize;
        updateFontSizeOffset();
        prefs.put(APPEARANCE_FONT_SIZE_ACTIVE, fontSize);

        PropertyChangeEvent evt = new PropertyChangeEvent(
                getNormalFont(),
                "Font",
                oldFont,
                newFont
        );

        propertyChangeSupport.firePropertyChange(evt);
    }

    /**
     * This will return the preferences object.
     * @return Preferences object.
     */
    public Preferences getPrefs() {
        return prefs;
    }

    /**
     * Returns the current active theme.
     * @return Active theme
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * This will return with the applications root path.
     * @return Applications root path
     */
    public String getAppRootPath() {
        return rootPath;
    }

    /**
     * Get a list of all available font sizes (ASCII description).
     * @return List of availavbe font sizes.
     */
    public ArrayList<String> getFontSizes() {
        return fontSizes;
    }

    /**
     * Get the active font size (ASCII description).
     * @return Active font size (ASCII description).
     */
    public String getFontSize() {
        return activeFontSize;
    }
}
